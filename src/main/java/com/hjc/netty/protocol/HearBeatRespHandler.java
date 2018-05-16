package com.hjc.netty.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author : Hjc
 * @date : 2018/5/15 0015 10:15
 * @description : 服务端心跳
 */
public class HearBeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String msgStr = (String) msg;
        NettyMessage message = JSON.parseObject(msgStr,NettyMessage.class);
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("Receive client heart beat message : -->" + message);
            NettyMessage heartBeat = buildHeartBeat();
            System.out.println("Send heart beat response message to client : -->" + heartBeat);
            ctx.writeAndFlush(Unpooled.copiedBuffer((JSON.toJSONString(heartBeat)+"$_").getBytes()));
        }else {
            ctx.fireChannelRead(Unpooled.copiedBuffer((msgStr+"$_").getBytes()));
        }
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
