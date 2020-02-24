package com.fosuchao.rpc.protocol.message;

import lombok.Data;

/**
 * @description: Rpc请求消息体
 * @author: Joker Ye
 * @create: 2020/2/24 16:06
 */
@Data
public class RpcRequest {
    // 请求id
    private String requestId;

    // 调用的类名
    private String ClassName;

    // 调用的方法名
    private String MethodName;

    // 调用的参数类型列表
    private Class<?>[] paramTypes;

    // 调用的参数类型
    private Object[] params;
}
