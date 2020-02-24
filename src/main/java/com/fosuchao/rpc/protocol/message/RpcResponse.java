package com.fosuchao.rpc.protocol.message;

import lombok.Data;

/**
 * @description: Rpc响应的消息体
 * @author: Joker Ye
 * @create: 2020/2/24 16:13
 */
@Data
public class RpcResponse {
    // request id
    private String requestId;

    // 返回结果
    private Object res;

    // 异常
    private Throwable throwable;

}
