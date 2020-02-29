package com.fosuchao.heartbeatsimple;

import io.netty.handler.timeout.IdleState;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/14 14:22
 */
public class StateUtil {

    public static String getState(IdleState state) {
        String res = null;
        switch (state) {
            case ALL_IDLE: res = "读写空闲"; break;
            case READER_IDLE: res = "读空闲"; break;
            case WRITER_IDLE: res = "写空闲"; break;
            default: break;
        }
        return res;
    }

}
