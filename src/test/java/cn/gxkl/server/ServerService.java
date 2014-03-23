package cn.gxkl.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

import cn.mars.gxkl.mainUI.Constant;
import cn.mars.gxkl.netty.NettyHandler;
import cn.mars.gxkl.protocol.AppProtocol;
import cn.mars.gxkl.protocol.FrontEndingCommunicationProtocol;
import cn.mars.gxkl.protocol.LiveMessageProtocol;
import cn.mars.gxkl.utils.LinkedList;

import com.google.gson.Gson;

public class ServerService {
	private int port;
	private String address;
	private Channel channel;
	private NettyHandler handler;
	private Gson gson = new Gson();
	private boolean connected = false;

	public ServerService(int port) {
		this.port = port;
		this.address = "127.0.0.1";
		init();
	}

	public ServerService(int port, String address) {
		this.port = port;
		this.address = address;
		init();
	}

	public void init() {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		
		handler = new NettyHandler() {
			@Override
			public void messageHandler(String message) {
				// TODO Auto-generated method stub
				System.out.println("Server recieve: "+message);
			}
			@Override
			public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
				channel = e.getChannel();
				connected = true;
			}
		};
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("DOWN_FRAME_HANDLER", new LengthFieldPrepender(2, false));
				pipeline.addLast("UP_FRAME_HANDLER", new LengthFieldBasedFrameDecoder(
						Integer.MAX_VALUE, 0, 2, 0, 2));
				pipeline.addLast("myHandler", handler);
				return pipeline;
			}
		});
		bootstrap.bind(new InetSocketAddress(port));
	}

	public void sendMessage(String msg) {
		handler.sendMessage(msg, channel);
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public String getMessage() {
		AppProtocol msg = new AppProtocol();
		msg.setAuthenticate("confirm");
		msg.setTargetUrl("testURL");
		msg.setStatus("normal");
		
		FrontEndingCommunicationProtocol<LiveMessageProtocol> hehe = new FrontEndingCommunicationProtocol<LiveMessageProtocol>();
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("operateType", "search");
		condition.put("tableType", "manage");
		condition.put("status", "OK");
		condition.put("offset", 0);
		condition.put("length", 10);
		hehe.setCondition(condition);
		List<LiveMessageProtocol> xixi = new ArrayList<LiveMessageProtocol>();
		for(int j=0;j<50;j++) {
			xixi.add(getLiveMessage(false));
		}
		hehe.setRows(xixi);
		
		Gson gson = new Gson();
		msg.setContent(gson.toJson(hehe));
		msg.setResponse("json");
		return gson.toJson(msg);
	}
	
	public LiveMessageProtocol getLiveMessage(boolean emerg) {
		LiveMessageProtocol ret = new LiveMessageProtocol();
//		Map<String,Object> condition = new HashMap<String,Object>();
		Random random = new Random();
		String processName = Constant.processName[random.nextInt(5)];
		if(emerg)
			processName = "Emergency";
		ret.setProcessName(processName);
		int id = random.nextInt(10);
		if(emerg)
			id = -1;
		ret.setId(id);
		List<String> retValue = new ArrayList<String>();
		for(int i=0;i<2;i++) {
			retValue.add("There is something coming... in "+processName+" id "+id);
		}
//		ret.setRetValue(retValue);
		return ret;
	}

	public String getEmergency() {
		AppProtocol msg = new AppProtocol();
		msg.setAuthenticate("confirm");
		msg.setTargetUrl("testURL");
		msg.setStatus("emergency");
		
		FrontEndingCommunicationProtocol<LiveMessageProtocol> hehe = new FrontEndingCommunicationProtocol<LiveMessageProtocol>();
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("operateType", "search");
		condition.put("tableType", "manage");
		condition.put("status", "OK");
		condition.put("offset", 0);
		condition.put("length", 10);
		hehe.setCondition(condition);
		List<LiveMessageProtocol> xixi = new ArrayList<LiveMessageProtocol>();
		int index = new Random().nextInt(10)+1;
		for(int i=0;i<index;i++) {
			xixi.add(getLiveMessage(true));
		}
		hehe.setRows(xixi);
		Gson gson = new Gson();
		msg.setContent(gson.toJson(hehe));
		msg.setResponse("json");
		return gson.toJson(msg);
	}
	
	public static void main(String[] args) {
		ServerService ss = new ServerService(8970);
		while(!ss.isConnected()) {
			System.out.println("connecting...");
		}
		Random random = new Random();
		while(true) {
			int index = random.nextInt(100);
			if(index<96)
				ss.sendMessage(ss.getMessage());
			else
				ss.sendMessage(ss.getEmergency());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
