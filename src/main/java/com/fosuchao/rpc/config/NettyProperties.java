package com.fosuchao.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:10
 */
@Data
@ConfigurationProperties
public class NettyProperties {

    private Integer tcpPort = 8899;

    private String tcpHost = "127.0.0.1";

    private Integer bossCount = 1;

    private Integer workCount = Runtime.getRuntime().availableProcessors() * 2;
}
