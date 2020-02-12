package com.fosuchao.groupchat.client;

import com.fosuchao.groupchat.initalizer.ClientChatInitalizer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/12 13:48
 */
public class TcpChatClient {
    private static final int PORT = 8899;
    private static final String HOST = "127.0.0.1";

    public void start() throws InterruptedException {
        // 客户端连接组
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 设置
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChatInitalizer());

        ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
        //得到channel
        Channel channel = channelFuture.channel();
        System.out.println("-------" + channel.localAddress() + "--------");
        //客户端需要输入信息，创建一个扫描器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            //通过channel 发送到服务器端
            channel.writeAndFlush(msg + "\r\n");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TcpChatClient().start();
    }
}
