package com.fosuchao.rpc.client.future;

import com.fosuchao.rpc.protocol.message.RpcResponse;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 13:14
 */
public class DefaultFuture {
    private boolean success = false;
    private RpcResponse response;

    public synchronized RpcResponse getResponse(int timeout) {
        while (!success) {
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public synchronized void setResponse(RpcResponse response) {
        if (success) {
            return;
        }
        this.response = response;
        this.success = true;
        this.notify();
    }
}
