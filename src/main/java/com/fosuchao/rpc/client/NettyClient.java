package com.fosuchao.rpc.client;

import com.fosuchao.rpc.client.handler.ClientHandler;
import com.fosuchao.rpc.config.NettyConfig;
import com.fosuchao.rpc.config.NettyProperties;
import com.fosuchao.rpc.protocol.message.RpcRequest;
import com.fosuchao.rpc.protocol.message.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 14:01
 */
@Component
public class NettyClient {
    @Autowired
    NettyConfig nettyConfig;

    @Autowired
    NettyProperties nettyProperties;

    Channel channel;

    public RpcResponse send(RpcRequest request) {
        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ClientHandler.getResponse(request.getRequestId());
    }

    public void connent() {
        Bootstrap bootstrap = nettyConfig.bootstrap();
        try {
            channel = bootstrap
                        .connect(nettyProperties.getTcpHost(), nettyProperties.getTcpPort())
                        .sync()
                        .channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
