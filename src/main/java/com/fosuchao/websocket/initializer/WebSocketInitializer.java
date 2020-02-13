package com.fosuchao.websocket.initializer;

import com.fosuchao.websocket.config.NettyConfig;
import com.fosuchao.websocket.handler.WebSocketIdleHandler;
import com.fosuchao.websocket.handler.WebSocketMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/13 11:40
 */
public class WebSocketInitializer  extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();

        // 处理第一次连接http的握手请求
        pipeline.addLast(new HttpServerCodec());
        // 写文件内容
        pipeline.addLast(new ChunkedWriteHandler());
        // 保证接收的http请求的完整性
        pipeline.addLast(new HttpObjectAggregator
                (NettyConfig.WebSocket.HttpObjectArrgregator.getMaxLength()));
        // 处理其他的WebSocketFrame
        pipeline.addLast(new WebSocketServerProtocolHandler(NettyConfig.WebSocket.getPATH()));

        // 添加自己的处理器
        // 心跳监测
        pipeline.addLast(new WebSocketIdleHandler());
        // 消息处理
        pipeline.addLast(new WebSocketMessageHandler());
    }
}
