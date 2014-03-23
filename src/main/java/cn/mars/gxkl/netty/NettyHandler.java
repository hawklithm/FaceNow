package cn.mars.gxkl.netty;

import java.io.IOException;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public abstract class NettyHandler extends SimpleChannelHandler {

	@Override
	final public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
//		System.out.println("receive: " + buffer.toString(Charset.defaultCharset()));
		messageHandler(buffer.toString(Charset.defaultCharset()));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		if (e.getCause() instanceof IOException) {
			System.out.println("连接错误,断开");
			ctx.getChannel().close();
		} else {
			e.getCause().printStackTrace();
		}
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.println("channelDisconnect");
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channalClosed");
		super.channelClosed(ctx, e);
	}

	public void sendMessage(String message, Channel channel) {
		if(channel == null) {
			System.out.println("null channel,can't send message.");
			return ;
		}
		byte[] messageBytes=message.getBytes();
		ChannelBuffer buffer = ChannelBuffers.buffer(messageBytes.length);
		buffer.writeBytes(messageBytes);
		channel.write(buffer);
	}
	
	/**
	  * 
	  * 信息处理
	  *
	  **/
	public abstract void messageHandler(String message);
}
