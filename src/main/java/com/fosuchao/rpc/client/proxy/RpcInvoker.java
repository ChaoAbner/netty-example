package com.fosuchao.rpc.client.proxy;

import com.fosuchao.rpc.client.Sender;
import com.fosuchao.rpc.protocol.message.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 14:08
 */
public class RpcInvoker implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 代理对象
        RpcRequest rpcRequest = new RpcRequest();
        String uuid = UUID.randomUUID().toString();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        rpcRequest.setRequestId(uuid);
        rpcRequest.setClassName(className);
        rpcRequest.setMethodName(methodName);
        rpcRequest.setParamTypes(parameterTypes);
        rpcRequest.setParams(args);

        return Sender.send(rpcRequest).getRes();
    }
}
