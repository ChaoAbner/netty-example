package com.fosuchao.heartbeatsimple.initalizer;

import com.fosuchao.heartbeatsimple.handler.HeartbeatServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/14 13:56
 */
public class HeartbeatInitalizer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();

        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatServerHandler());
    }
}
