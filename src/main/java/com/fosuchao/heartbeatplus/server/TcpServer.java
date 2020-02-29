package com.fosuchao.heartbeatplus.server;

import com.fosuchao.heartbeatplus.server.initializer.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: 服务端
 * @author: Joker Ye
 * @create: 2020/2/29 11:01
 */
public class TcpServer {
    private static Integer port;

    public TcpServer(int port) {
        this.port = port;
    }

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());

        try {
            Channel channel = serverBootstrap.bind(port).sync().channel();
            System.out.println("server start success, port at: " + port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TcpServer(8899).start();
    }
}
