package com.fosuchao.rpc.protocol.serializations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fosuchao.rpc.protocol.Serialization;

import java.io.IOException;

/**
 * @description: 使用json实现序列化操作
 * @author: Joker Ye
 * @create: 2020/2/24 15:57
 */
public class JsonSerialization implements Serialization {
    ObjectMapper objectMapper;

    public JsonSerialization() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> byte[] serialize(T t) {
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public <T> T deSerialize(byte[] data, Class<T> clz) {
        T t = null;
        try {
            t = objectMapper.readValue(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
