package com.fosuchao.rpc.client.proxy;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 14:02
 */
public class ProxyFactory {
    /** @noinspection unchecked*/
    public static <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvoker()
        );
    }
}
