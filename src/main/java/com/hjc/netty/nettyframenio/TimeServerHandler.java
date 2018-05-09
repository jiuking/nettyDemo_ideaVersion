package com.hjc.netty.nettyframenio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hjc
 * @date 2018-05-09
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        String body = new String(bytes, "UTF-8").substring(0,bytes.length-System.getProperty("line.separator").length());
        String body = (String) msg;
        System.out.println("The time server receive order:" + body+",the counter is:"+ ++counter);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = "QUERY TIME ORDER".equals(body) ? dateFormat.format(new Date()) : "BAD REQUEST";
        //不添加该行客户端收不到该返回的消息
        currentTime += System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
