package com.hjc.netty.nettyframenio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : Administrator
 * @date : 2018/5/9 0009 14:22
 * @description : 时间客户端
 */
public class TimeClient {
    public static void main(String[] args) throws InterruptedException {
        new TimeClient().connect("127.0.0.1", 8095);
    }

    private void connect(String s, int i) throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new TimeClientHandler());
                }
            });

            ChannelFuture f = bootstrap.connect(s, i).sync();

            f.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
