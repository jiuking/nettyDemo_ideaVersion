package com.hjc.netty.nettyframenio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author : Administrator
 * @date : 2018/5/9 0009 14:30
 * @description : 时间客户端Handler
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{

    private int counter;
    private byte[] req;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
//        req = "QUERY TIME ORDER".getBytes();
    }

    /**
     * @Author: Hjc
     * @Description: 连接完成之后发送
     * @param: ctx
     * @Date: 14:45 2018/5/9 0009
     * @throws: Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf;
        for (int i = 0; i < 100; i++) {
            byteBuf = Unpooled.buffer(req.length);
            byteBuf.writeBytes(req);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        String body = byteBuf.toString(CharsetUtil.UTF_8);
        String body = (String) msg;
        System.out.println("Now is  :"+body+"; the counter"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
