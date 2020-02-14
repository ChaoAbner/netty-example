package com.fosuchao.heartbeat.handler;

import com.fosuchao.heartbeat.StateUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/14 15:25
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {
    private int lossConnectCount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("已经15秒未收到客户端的消息了！");
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            System.out.println("userEventTriggered触发，当前状态" + StateUtil.getState(state));
            lossConnectCount++;
            if (lossConnectCount > 2) {
                System.out.println("关闭这个不活跃通道");
                ctx.channel().close();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client【"+ ctx.channel().remoteAddress() +"】connected~~");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client says: "+msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭");
    }
}
