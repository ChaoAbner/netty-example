package com.fosuchao.rpc.service.impl;

import com.fosuchao.rpc.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 11:06
 */
@Service
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello " + name;
    }
}
