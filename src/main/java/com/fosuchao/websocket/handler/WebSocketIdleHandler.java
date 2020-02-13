package com.fosuchao.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/13 12:48
 */
public class WebSocketIdleHandler extends IdleStateHandler {
    private static final int IDLE_TIME = 15;

    public WebSocketIdleHandler() {
        super(IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        ctx.channel().close();
    }
}
