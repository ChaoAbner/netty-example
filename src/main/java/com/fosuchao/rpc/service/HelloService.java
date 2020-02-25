package com.fosuchao.rpc.service;

import com.fosuchao.rpc.annotations.RpcInterface;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:06
 */
@RpcInterface
public interface HelloService {
    String hello(String name);
}
