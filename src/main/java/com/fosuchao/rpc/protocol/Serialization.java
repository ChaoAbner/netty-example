package com.fosuchao.rpc.protocol;

// 序列化接口
public interface Serialization {
    // 将对象序列化为字节数组
    <T> byte[] serialize(T t);

    // 反序列化
    <T> T deSerialize(byte[] data, Class<T> clz);
}
