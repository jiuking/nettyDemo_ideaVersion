package com.hjc.netty.websocketnio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author : Administrator
 * @date : 2018/5/10 0010 17:34
 * @description : WebSocket服务类启动类
 */
public class WebSocketServer {
    public void run(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup).channel(ServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
                socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                socketChannel.pipeline().addLast("handler", new WebSocketServerHandler());
            }
        });
        try {
            Channel ch = bootstrap.bind(port).sync().channel();
            System.out.println("Open you browser and navigate to http://localhost:"+port);
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
