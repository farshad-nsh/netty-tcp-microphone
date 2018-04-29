package com.tel.speech.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

public class SpeechServerHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String speech)
			throws Exception {

		System.out.println(ctx.channel().remoteAddress() + " speech : " + speech);

		ctx.writeAndFlush("Received your speech. speech=" + speech + "\n");
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("RemoteAddress : " + ctx.channel().remoteAddress()
				+ " active !");

		ctx.writeAndFlush("Welcome to "
				+ InetAddress.getLocalHost().getHostName() + " persian speech recognition services!\n");

		super.channelActive(ctx);
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("channel is now inactive");
	}
}
