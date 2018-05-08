package com.hjc.netty.aio;

/**
 * @author Hjc
 * @date 2018-05-08
 */
public class TimeServer {
    public static void main(String[] args) {
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(8093);
        new Thread(timeServerHandler,"AIO-TimeServer").start();
    }
}
