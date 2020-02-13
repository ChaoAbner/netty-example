package com.fosuchao.websocket.server;

import com.fosuchao.websocket.initializer.WebSocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/13 11:36
 */
public class WebSocketServer {
    private static final int PORT = 8899;

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
//                .option(ChannelOption.SO_KEEPALIVE, )
                .childHandler(new WebSocketInitializer());

        serverBootstrap.bind(8899).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Websocket服务启动成功");
                } else {
                    System.out.println("Websocket服务启动失败");
                }
            }
        });
    }

    public static void main(String[] args) {
        new WebSocketServer().start();
    }
}
