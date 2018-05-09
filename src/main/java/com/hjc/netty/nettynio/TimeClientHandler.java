package com.hjc.netty.nettynio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hjc
 * @date 2018-05-08
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private final ByteBuf firstMessage;
    byte[] reqNew;

    public TimeClientHandler() {
        logger.info("asdf");
        byte[] req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        reqNew = req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("New is :" + body);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf msg = null;
        for (int i = 0; i < 100; i++) {
            msg = Unpooled.buffer(reqNew.length);
            msg.writeBytes(reqNew);
            ctx.writeAndFlush(msg);
        }
//        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn(cause.getMessage());
        ctx.close();
    }
}
