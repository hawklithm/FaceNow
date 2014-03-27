package cn.mars.gxkl.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

import cn.mars.gxkl.protocol.AppProtocol;

import com.google.gson.Gson;

public class ClientService {
	private int port;
	private String address;
	private Channel channel;
	private NettyHandler handler;
	private boolean connected = false,ack = false,connectionStatus = true;
	ConcurrentLinkedQueue<AppProtocol> infoCache;
	ConcurrentLinkedQueue<AppProtocol> emergCache;
	
	public ClientService(int port) {
		this.port = port;
		this.address = "127.0.0.1";
		initialization();
	}
	
	public ClientService(String address,int port) {
		this.address = address;
		this.port = port;
		initialization();
	}
	
	private void initialization() {
		
		infoCache = new ConcurrentLinkedQueue<AppProtocol>();
		emergCache = new ConcurrentLinkedQueue<AppProtocol>();
		
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),Executors.newCachedThreadPool()));
		handler = new NettyHandler() {
			@Override
			public void messageHandler(String message) {
				// TODO Auto-generated method stub
				System.out.println("Client recieve: "+message);
//				AppProtocol response = decoder(message);
				Gson gson = new Gson();
				AppProtocol response = gson.fromJson(message, AppProtocol.class);
				String status = response.getStatus();
				if(status.equals("ok")) {
					ack = true;
				}
				else if(status.equals("fail")) {
					connectionStatus = false;
				}
				if(status.equals("warning")) {
					emergCache.add(response);
				}
				else if(status.equals("connected")){
					System.out.println("add message to buffer");
					infoCache.add(response);
				}
			}
			@Override
			public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
				System.out.println("Connect successfullly!");
				channel = e.getChannel();
				connected = true;
			}
		};
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				// TODO Auto-generated method stub
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("DOWN_FRAME_HANDLER", new LengthFieldPrepender(2, false));
				pipeline.addLast("UP_FRAME_HANDLER", new LengthFieldBasedFrameDecoder(
						Integer.MAX_VALUE, 0, 2, 0, 2));
				pipeline.addLast("clientHandler", handler);
				return pipeline;
			}
		});
		bootstrap.connect(new InetSocketAddress(address, port));
	}
	
	public boolean getACK() {
		return ack;
	}
	
	public boolean getConnectionStatus() {
		return connectionStatus;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void sendMessage(String msg) {
		ack = false;
		connectionStatus = true;
		handler.sendMessage(msg, channel);
	}
	
	public AppProtocol getMessage() {
		if(infoCache.isEmpty()) {
			return null;
		}
		return infoCache.remove();
	}
	
	public AppProtocol getEmerg() {
		if(emergCache.isEmpty()) {
			return null;
		}
		return emergCache.remove();
	}
	
	public void flushInfoCache() {
		infoCache.clear();
	}
	
	public void flushEmergCache() {
		emergCache.clear();
	}
	
	public void closeClient() {
		/***
		 * 
		 *关闭
		 * 
		 ***/
	}
	
}
