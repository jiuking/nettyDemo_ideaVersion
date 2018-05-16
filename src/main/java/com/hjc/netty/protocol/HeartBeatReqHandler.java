package com.hjc.netty.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 17:51
 * @description : 心跳请求
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter{

    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String msgStr = (String) msg;
        System.out.println(msgStr);
        NettyMessage message = JSON.parseObject(msgStr, NettyMessage.class);
        if (message.getHeader() != null&&message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
        } else if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()) {
            System.out.println("Client receive server heart beat message: -->" + message);
        } else {
            ctx.fireChannelRead(Unpooled.copiedBuffer((msgStr + "$_").getBytes()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    private class HeartBeatTask implements Runnable{

        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext context) {
            ctx = context;
        }

        @Override
        public void run() {
            NettyMessage heatBeat = buildHeartBeat();
            System.out.println("Client send heart beat message to server ：--->" + heatBeat);
            ctx.writeAndFlush(Unpooled.copiedBuffer((JSON.toJSONString(heatBeat) + "$_").getBytes()));
        }

        private NettyMessage buildHeartBeat() {
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }
}
