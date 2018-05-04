package com.hjc.netty.nio;

/**
 * @Author: Hjc
 * @Description: 时间服务器
 * @param: null
 * @Date: 17:36 2018/5/4 0004
 * @return:
 * @throws:
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8083;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
