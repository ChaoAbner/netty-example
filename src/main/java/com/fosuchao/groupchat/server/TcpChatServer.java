package com.fosuchao.groupchat.server;

import com.fosuchao.groupchat.initalizer.ServerChatInitalizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/12 12:32
 */
public class TcpChatServer {
    private static final int PORT = 8899;

    public void start() {
        // 定义调度线程，工作线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        // 开始引导netty服务器设置
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 指定线程模型，这里是主从模型
                .group(bossGroup, workGroup)
                // 指定服务端Channel的I/O模型
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 添加处理新连接的initializer,即handler处理器
                .childHandler(new ServerChatInitalizer());

        System.out.println("netty 服务器启动");
        try {
            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            //监听关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new TcpChatServer().start();
    }

}
