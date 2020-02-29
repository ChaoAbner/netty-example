package com.fosuchao.heartbeatplus.client.initializer;

import com.fosuchao.heartbeatplus.client.TcpClient;
import com.fosuchao.heartbeatplus.client.handler.PingerHandler;
import com.fosuchao.heartbeatplus.client.handler.ReconnectHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/29 11:25
 */
public class ClientInitializer extends ChannelInitializer {
    TcpClient tcpClient;

    public ClientInitializer(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("StringDecoder", new StringDecoder());
        pipeline.addLast("StringEncoder", new StringEncoder());
        pipeline.addLast("PingHandler", new PingerHandler());
        pipeline.addLast("ReconnectHandler", new ReconnectHandler(tcpClient));
    }
}
