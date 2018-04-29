package com.tel.speech.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SpeechServer {
	private static final int portNumber = 3001;

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new SpeechServerInitializer());

			/**
			 * bind() returns a ChannelFuture and sync() will wait until this future isDone.
			 */
			ChannelFuture channelFuture = b.bind(portNumber);
			channelFuture.sync();

			Channel channel = channelFuture.channel();
			ChannelFuture closeFuture = channel.closeFuture();
			closeFuture.sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
