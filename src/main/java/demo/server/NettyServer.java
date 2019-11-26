package demo.server;

import demo.handler.ServerHandler;
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
    //IP地址
    private  static  String IP="127.0.0.1";
    //默认端口
    private  static  int port = 5566;
    // 组的数量
    private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
    // 头部数量
    private static final int BIZHEADSIZE = 100;
    // 创建领导组
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);

    // 创建工作组
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZHEADSIZE);

    public static void init() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 添加组
        serverBootstrap.group(bossGroup, workerGroup);

        // 声明通道
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel channel) throws Exception {
                // pipeline 管理channel中的handler
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new ServerHandler());

            }
        });

        System.out.println("服务器信息，IP为:" + IP + "，端口为：" + port);
        // 绑定server,通过调用sync方法同步方法阻塞知道绑定成功
        ChannelFuture sync = serverBootstrap.bind(IP, port).sync();
        sync.channel().closeFuture().sync();
        System.out.println("服务器启动成功");
    }
    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("开始启动TCP服务器...");
        NettyServer.init();
//         HelloServer.shutdown();

    }


}
