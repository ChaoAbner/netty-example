package com.fosuchao.heartbeatplus.client.handler;

import com.fosuchao.heartbeatplus.client.TcpClient;
import com.fosuchao.heartbeatplus.client.reconnect.RetryPolicy;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * @description: 重连控制器
 * @author: Joker Ye
 * @create: 2020/2/29 12:14
 */
@ChannelHandler.Sharable
public class ReconnectHandler extends ChannelInboundHandlerAdapter {
    // 接收一个Client
    private TcpClient tcpClient;
    // 重试次数
    private static int retryCount = 0;

    private RetryPolicy retryPolicy;

    public ReconnectHandler(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Successfully established a connection to the server.");
        // 重连成功，重置重连次数
        retryCount = 0;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 断开重连
        RetryPolicy retryPolicy = getRetryPolicy();
        if (retryPolicy.allowRetry(retryCount)) {
            long sleepTimeMs = retryPolicy.getSleepTimeMs(retryCount);
            System.out.println("try to reconnect server after " + sleepTimeMs
                    + "ms. Retry count: " + ++retryCount);
            EventLoop eventLoop = ctx.channel().eventLoop();
            eventLoop.schedule(() -> {
                System.out.println("Reconnecting..");
                tcpClient.connect();
            }, sleepTimeMs, TimeUnit.MILLISECONDS);
        }
    }

    private RetryPolicy getRetryPolicy() {
        if (retryPolicy == null) {
            retryPolicy = tcpClient.getRetryPolicy();
        }
        return retryPolicy;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
