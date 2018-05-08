package com.hjc.netty.aio;

/**
 * @author Hjc
 * @date 2018-05-08
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1",8093),"AIO-Asy").start();
    }
}
