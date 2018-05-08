package com.hjc.netty.nio;

/**
 *
 * @author Hjc
 * @date 2018-05-07
 */
public class NioClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandle("127.0.0.1",8083)).start();
    }
}
