package com.fosuchao.heartbeatplus.server.initializer;

import com.fosuchao.heartbeatplus.server.handler.IdleTriggerHandler;
import com.fosuchao.heartbeatplus.server.handler.ServerBizHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/29 11:07
 */
public class ServerInitializer extends ChannelInitializer {
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("idleStateHandler", new IdleStateHandler(5, 0, 0));
        pipeline.addLast("idleTriggerHandler", new IdleTriggerHandler());
        pipeline.addLast("StringDecoder", new StringDecoder());
        pipeline.addLast("StringEncoder", new StringEncoder());
        pipeline.addLast("BizHandler", new ServerBizHandler());
    }
}
