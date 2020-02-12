package com.fosuchao.groupchat.initalizer;

import com.fosuchao.groupchat.handler.ClientChatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/12 15:08
 */
public class ClientChatInitalizer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new ClientChatHandler());
    }
}
