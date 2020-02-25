package com.fosuchao.rpc.client;

import com.fosuchao.rpc.protocol.message.RpcRequest;
import com.fosuchao.rpc.protocol.message.RpcResponse;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 14:17
 */
public class Sender {
    public static RpcResponse send(RpcRequest request) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.connent();
        return nettyClient.send(request);
    }
}
