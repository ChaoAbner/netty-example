package com.fosuchao.rpc.server.initializer;

import com.fosuchao.rpc.protocol.codec.RpcDecoder;
import com.fosuchao.rpc.protocol.codec.RpcEncoder;
import com.fosuchao.rpc.protocol.message.RpcRequest;
import com.fosuchao.rpc.protocol.message.RpcResponse;
import com.fosuchao.rpc.protocol.serializations.JsonSerialization;
import com.fosuchao.rpc.server.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:36
 */
public class ServerInitializer extends ChannelInitializer<NioServerSocketChannel> {
    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioServerSocketChannel.pipeline();

        pipeline.addLast(new RpcEncoder(new JsonSerialization(), RpcResponse.class));
        pipeline.addLast(new RpcDecoder(new JsonSerialization(), RpcRequest.class));
        pipeline.addLast(new ServerHandler());
    }
}
