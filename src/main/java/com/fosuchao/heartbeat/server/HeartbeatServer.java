package com.fosuchao.heartbeat.server;

import com.fosuchao.heartbeat.initalizer.HeartbeatInitalizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description: 心跳监测，当客户端一连接，便开始监测心跳。
 * @Auther: Joker Ye
 * @Date: 2020/2/14 13:51
 */
public class HeartbeatServer {
    private static final int PORT = 8899;

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HeartbeatInitalizer());

        serverBootstrap.bind(PORT).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("服务器启动成功");
                }
            }
        });
    }

    public static void main(String[] args) {
        new HeartbeatServer().start();
    }
}
