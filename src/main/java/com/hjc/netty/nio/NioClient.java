package com.hjc.netty.nio;

public class NioClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandle("127.0.0.1",8083)).start();
    }
}
