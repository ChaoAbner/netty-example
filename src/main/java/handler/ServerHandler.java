package handler;

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
        // 从管道读取数据
        System.out.println("server received data: " + msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 读取数据完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)    // flush调所有写回的数据
                .addListener(ChannelFutureListener.CLOSE);  // 当flash完成后关闭Channel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();    // 捕获异常信息
        ctx.close();            // 出现异常则关闭channel
    }
}
