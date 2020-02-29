package com.fosuchao.heartbeatplus.client;

import com.fosuchao.heartbeatplus.client.initializer.ClientInitializer;
import com.fosuchao.heartbeatplus.client.reconnect.DefaultRetryPolicy;
import com.fosuchao.heartbeatplus.client.reconnect.RetryPolicy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/29 11:18
 */
public class TcpClient {
    private String host;
    private Integer port;
    private Channel channel;
    private Bootstrap bootstrap;
    private RetryPolicy retryPolicy;

    public TcpClient(String host, int port) {
        this(host, port, new DefaultRetryPolicy(1000,
                Integer.MAX_VALUE, 60 * 1000));
    }

    public TcpClient(String host, int port, RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        this.host = host;
        this.port = port;
        init();
    }

    public void init() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(this));
    }

    public void connect() {
        synchronized (bootstrap) {
            ChannelFuture future = bootstrap.connect(host, port);
            future.addListener(getConnectionListener());
            channel = future.channel();
        }
    }

    private ChannelFutureListener getConnectionListener() {
        return new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    future.channel().pipeline().fireChannelInactive();
                } else {
                    System.out.println("Connect server success");
                }
            }
        };
    }

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }


    public static void main(String[] args) {
        TcpClient client = new TcpClient("localhost", 8899);
        client.connect();
    }
}
