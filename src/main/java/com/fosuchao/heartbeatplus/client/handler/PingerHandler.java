package com.fosuchao.heartbeatplus.client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description: 当客户端连接上服务端时，就会开启定时器循环发起ping
 * @author: Joker Ye
 * @create: 2020/2/29 11:35
 * @noinspection unchecked
 */
public class PingerHandler extends ChannelInboundHandlerAdapter {
    private Random random;
    private Integer baseRandom;
    private Channel channel;
    private String heartBeat = "heart beat";

    public PingerHandler() {
        this.random = new Random();
        this.baseRandom = 10;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        ping(channel);
    }

    private void ping(Channel channel) {
        // 至少一秒中ping一次
        int interval = Math.max(1, random.nextInt(baseRandom));
        System.out.println("next heart beat will be sent after " + interval + "s.");
        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                if (channel.isActive()) {
                    System.out.println("Send heart beat to server");
                    channel.writeAndFlush(heartBeat);
                } else {
                    System.out.println("The connection had broken..");
                    channel.closeFuture();
                }
            }
        }, interval, TimeUnit.SECONDS);

        future.addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    ping(channel);
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
