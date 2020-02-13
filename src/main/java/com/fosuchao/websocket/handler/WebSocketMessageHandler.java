package com.fosuchao.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/13 13:05
 */
public class WebSocketMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel channel = channelHandlerContext.channel();

        System.out.println("来自" + channel.remoteAddress() +"的消息：" + textWebSocketFrame.text());

        channel.writeAndFlush(new TextWebSocketFrame("服务器返回：" + textWebSocketFrame.text()));
    }
}
