package com.tel.hello.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MicrophoneClient {
	public static String host = "127.0.0.1";
	public static int port = 3001;

	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.channel(NioSocketChannel.class);
			b.handler(new MicrophoneClientInitializer());

			ChannelFuture connect = b.connect(host, port);
			connect.sync();
			Channel ch = connect.channel();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			for (;;) {
				String line = in.readLine();
				if (line == null) {
					continue;
				}
				/*
				 * DelimiterBasedFrameDecoder
				 */
				ch.writeAndFlush(line + "\ra\n");

				if ("quit".equalsIgnoreCase(line)) {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// The connection is closed automatically on shutdown.
			group.shutdownGracefully();
		}
	}
}
