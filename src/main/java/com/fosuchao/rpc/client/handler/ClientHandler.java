package com.fosuchao.rpc.client.handler;

import com.fosuchao.rpc.client.future.DefaultFuture;
import com.fosuchao.rpc.protocol.message.RpcRequest;
import com.fosuchao.rpc.protocol.message.RpcResponse;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 12:41
 */
public class ClientHandler extends ChannelDuplexHandler {
    // 存储requestId和Future的映射,以便可以取出对应的结果
    static ConcurrentHashMap<String, DefaultFuture> map = new ConcurrentHashMap<String, DefaultFuture>();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 写入数据的时候，添加映射
        if (msg instanceof RpcRequest) {
            RpcRequest Request = (RpcRequest) msg;
            map.put(Request.getRequestId(), new DefaultFuture());

        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取结果的时候，根据id获取相应Future中的结果，然后删除映射
        if (msg instanceof RpcResponse) {
            RpcResponse response = (RpcResponse) msg;
            DefaultFuture defaultFuture = map.get(response.getRequestId());
            defaultFuture.setResponse(response);
        }
        super.channelRead(ctx, msg);
    }

    public static RpcResponse getResponse(String requestId) {
        try {
            DefaultFuture defaultFuture = map.get(requestId);
            return defaultFuture.getResponse(5);
        } finally {
            map.remove(requestId);
        }
    }
}
