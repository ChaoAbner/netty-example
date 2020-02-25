package com.fosuchao.rpc.config;

import com.fosuchao.rpc.client.initializer.ClientInitializer;
import com.fosuchao.rpc.server.initializer.ServerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:15
 */

@Configuration
@EnableConfigurationProperties(NettyProperties.class)
public class NettyConfig {

    @Autowired
    NettyProperties nettyProperties;

    public ServerBootstrap serverBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup(), workGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());

        return serverBootstrap;
    }

    public Bootstrap bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ClientInitializer());

        return bootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(nettyProperties.getBossCount());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workGroup() {
        return new NioEventLoopGroup(nettyProperties.getWorkCount());
    }
}
