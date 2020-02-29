package com.fosuchao.heartbeatplus.client.reconnect;

// 重连策略接口，根据需要实现此接口
public interface RetryPolicy {
    Boolean allowRetry(int retryCount);

    long getSleepTimeMs(int retryCount);
}
