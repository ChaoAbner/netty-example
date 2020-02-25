package com.fosuchao.rpc.server;

import com.fosuchao.rpc.config.NettyConfig;
import com.fosuchao.rpc.config.NettyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:01
 */
@Component
public class NettyServer {
    @Autowired
    NettyProperties nettyProperties;

    @Autowired
    NettyConfig nettyConfig;

    public void start()  {
        try {
            nettyConfig.serverBootstrap()
                    .bind(nettyProperties.getTcpHost(), nettyProperties.getTcpPort())
                    .sync()
                    .channel()
                    .closeFuture()
                    .channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
