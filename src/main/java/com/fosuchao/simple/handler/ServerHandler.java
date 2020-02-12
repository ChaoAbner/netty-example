package com.fosuchao.simple.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: https://blog.csdn.net/qq_35357001/article/details/77715451
 * @Auther: Joker Ye
 * @Date: 2019/11/24 23:42
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // �ӹܵ���ȡ����
        System.out.println("com.fosuchao.simple.server received data: " + msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ��ȡ�������
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)    // flush������д�ص�����
                .addListener(ChannelFutureListener.CLOSE);  // ��flash��ɺ�ر�Channel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();    // �����쳣��Ϣ
        ctx.close();            // �����쳣��ر�channel
    }
}
