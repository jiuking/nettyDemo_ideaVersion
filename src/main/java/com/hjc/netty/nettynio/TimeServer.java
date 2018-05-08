package com.hjc.netty.nettynio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Hjc
 * @date 2018-05-08
 */
public class TimeServer {

    public void bind(int port) throws InterruptedException {
        //一个用于服务端接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //另一个用于服务SocketChannel的网络读写
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChildChannelHandler());
            //启动同步阻塞方法sync等待绑定操作完成
            ChannelFuture f = serverBootstrap.bind(port).sync();
            //等待服务端链路关闭之后，main函数才退出
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TimeServer().bind(8094);
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }
}
