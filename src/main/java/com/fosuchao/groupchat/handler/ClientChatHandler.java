package com.fosuchao.groupchat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/12 13:41
 */
public class ClientChatHandler extends SimpleChannelInboundHandler<String> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());
    }
}
