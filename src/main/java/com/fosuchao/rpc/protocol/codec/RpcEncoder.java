package com.fosuchao.rpc.protocol.codec;

import com.fosuchao.rpc.protocol.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description: 继承MessageToByteEncoder实现自定义Rpc编码器
 * @author: Joker Ye
 * @create: 2020/2/24 16:05
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> clz;
    private Serialization serialization;

    public RpcEncoder(Serialization serialization, Class<?> clz) {
        this.serialization = serialization;
        this.clz = clz;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext,
                          Object o, ByteBuf byteBuf) throws Exception {
        if (clz != null) {
            byte[] serializeData = serialization.serialize(o);
            byteBuf.writeInt(serializeData.length);
            byteBuf.writeBytes(serializeData);
        }
    }
}
