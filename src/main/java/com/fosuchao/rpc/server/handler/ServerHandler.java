package com.fosuchao.rpc.server.handler;

import com.fosuchao.rpc.protocol.message.RpcRequest;
import com.fosuchao.rpc.protocol.message.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:35
 */
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest>
        implements ApplicationContextAware {
    ApplicationContext applicationContext;

    protected void channelRead0(ChannelHandlerContext ctx,
                                RpcRequest rpcRequest) throws Exception {
        // 处理远程调用
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());

        Object res = handler(rpcRequest);
        rpcResponse.setRes(res);

        ctx.writeAndFlush(rpcResponse);
    }

    public Object handler(RpcRequest request) throws ClassNotFoundException, InvocationTargetException {
        // 调用类名
        Class<?> clz = Class.forName(request.getClassName());
        // 从容器中拿到调用的服务
        Object bean = applicationContext.getBean(clz);

        Class<?> beanClass = bean.getClass();
        // 调用方法名
        String method = request.getMethodName();
        // 调用参数类型列表
        Class<?>[] paramTypes = request.getParamTypes();
        // 调用参数
        Object[] params = request.getParams();

        FastClass proxy = FastClass.create(beanClass);
        FastMethod proxyMethod = proxy.getMethod(method, paramTypes);

        return proxyMethod.invoke(bean, params);

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
