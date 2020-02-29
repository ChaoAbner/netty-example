package com.fosuchao.heartbeatplus.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/29 11:50
 */
public class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {
    private static final String HEART_BEAT = "heart beat";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // 超时未写，发起心跳
                ctx.writeAndFlush(HEART_BEAT);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
