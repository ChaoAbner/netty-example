package com.fosuchao.heartbeatplus.client.reconnect;

public interface RetryPolicy {
    Boolean allowRetry(int retryCount);

    long getSleepTimeMs(int retryCount);
}
