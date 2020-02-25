package com.fosuchao.rpc.protocol.codec;

import com.fosuchao.rpc.protocol.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description: 继承byteToMessageDecoder实现自定义解码器
 * @author: Joker Ye
 * @create: 2020/2/24 16:05
 */
public class RpcDecoder extends ByteToMessageDecoder{
    private Class<?> clz;
    private Serialization serialization;

    public RpcDecoder(Serialization serialization, Class<?> clz) {
        this.clz = clz;
        this.serialization = serialization;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            // 可读字节数小于4则return
            return;
        }

        // 标记当前缓冲区的readerIndex
        byteBuf.markReaderIndex();
        // 读取4个字节
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] data = new byte[length];
        // 将缓冲区的字节写到data
        byteBuf.readBytes(data);
        // 通过自定义序列化器进行反序列化
        Object o = serialization.deSerialize(data, clz);
        list.add(o);
    }
}
