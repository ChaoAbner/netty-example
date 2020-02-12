package com.fosuchao.groupchat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/12 13:23
 */
public class ServerChatHandler extends SimpleChannelInboundHandler<String> {
    //定义一个channle 组，管理所有的channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel currentChannel = ctx.channel();
        // 服务器收到消息
        System.out.println("收到【客户】" + currentChannel.remoteAddress() + "的消息：" + s);
        // 转发给其它客户端
        for (Channel channel : channelGroup) {
            if (channel != currentChannel)
                channel.writeAndFlush("【客户】" + currentChannel.remoteAddress() +"的消息：" + s + "\n");
            else
                channel.writeAndFlush("【我】" + currentChannel.remoteAddress() +"发送的消息：" + s + "\n");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(channel.remoteAddress() + "加入了聊天室");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(channel.remoteAddress() + "离开了聊天室");
        channelGroup.remove(channel);
        System.out.println("当前连接数：" + channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户】" + ctx.channel().remoteAddress() + "上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户】" + ctx.channel().remoteAddress() + "下线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
