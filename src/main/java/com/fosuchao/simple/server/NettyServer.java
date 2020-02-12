package com.fosuchao.simple.server;

import com.fosuchao.simple.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;



/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2019/11/25 19:46
 */
public class NettyServer {
    //IP��ַ
    private  static  String IP="127.0.0.1";
    //Ĭ�϶˿�
    private  static  int port = 5566;
    // �������
    private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
    // ͷ������
    private static final int BIZHEADSIZE = 100;
    // �����쵼��
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);

    // ����������
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZHEADSIZE);

    public static void init() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // �����
        serverBootstrap.group(bossGroup, workerGroup);

        // ����ͨ��
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel channel) throws Exception {
                // pipeline ����channel�е�handler
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new ServerHandler());

            }
        });

        System.out.println("��������Ϣ��IPΪ:" + IP + "���˿�Ϊ��" + port);
        // ��server,ͨ������sync����ͬ����������֪���󶨳ɹ�
        ChannelFuture sync = serverBootstrap.bind(IP, port).sync();
        sync.channel().closeFuture().sync();
        System.out.println("�����������ɹ�");
    }
    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("��ʼ����TCP������...");
        NettyServer.init();
//         HelloServer.shutdown();

    }


}
