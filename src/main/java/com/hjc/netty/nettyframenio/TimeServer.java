package com.hjc.netty.nettyframenio;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : Administrator
 * @date : 2018/5/9 0009 14:23
 * @description : 时间服务器
 */
public class TimeServer {
    public static void main(String[] args) throws InterruptedException {
        new TimeServer().bind(8095);
    }

    private void bind(int i) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());
        try {
            ChannelFuture f = bootstrap.bind(i).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            //LineBasedFrameDecoder工作原理是依次遍历ByteBuf中的可读字节，判断看是否有\r\r或\n。
            //如果连续读取最长长度仍然没有发现换行符，就抛出异常，同时忽略掉之前读取到的异常数据流。
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            //接收到的对象转换为字符串
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }
}
