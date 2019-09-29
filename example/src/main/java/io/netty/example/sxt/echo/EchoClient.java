package io.netty.example.sxt.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
	private final int port;
	private final String host;

	public EchoClient(int port, String host) {
		this.port = port;
		this.host = host;
	}

	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class) // 指明使用NIO进行网络通讯
					.remoteAddress(new InetSocketAddress(host, port))
					.handler(new EchoClientHandler());
			ChannelFuture f = b.connect().sync();// 连接到远程节点 ，阻塞直到连接完成
			f.channel().closeFuture().sync(); // 阻塞 直到channel 关闭
		} finally {
			group.shutdownGracefully().sync();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		new EchoClient(9999, "127.0.0.1").start();
	}

}
