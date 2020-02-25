package com.fosuchao.rpc.client.initializer;

import com.fosuchao.rpc.client.handler.ClientHandler;
import com.fosuchao.rpc.protocol.codec.RpcDecoder;
import com.fosuchao.rpc.protocol.codec.RpcEncoder;
import com.fosuchao.rpc.protocol.message.RpcRequest;
import com.fosuchao.rpc.protocol.message.RpcResponse;
import com.fosuchao.rpc.protocol.serializations.JsonSerialization;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 12:40
 */
public class ClientInitializer extends ChannelInitializer {

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new RpcEncoder(new JsonSerialization(), RpcResponse.class));
        pipeline.addLast(new RpcDecoder(new JsonSerialization(), RpcRequest.class));

        pipeline.addLast(new ClientHandler());
    }
}
