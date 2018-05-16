package com.hjc.netty.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.SourceTree;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.awt.*;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 16:00
 * @description : 握手认证客户端
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer((JSONObject.toJSONString(buildLoginReq())+"$_").getBytes()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        String msgStr = (String) msg;
        if (msgStr instanceof String) {
            System.out.println("msgStr is String");
        }
        NettyMessage message = JSON.parseObject(msgStr,NettyMessage.class);
        msgStr = msgStr + "$_";
        //如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            Integer loginResult = (Integer) message.getBody();
            if (loginResult != 0) {
                //握手失败，关闭连接
                System.out.println("关闭连接");
                ctx.close();
            } else {
                System.out.println("Login is ok:" + message);
                ctx.fireChannelRead(msgStr);
            }

        } else {
            ctx.fireChannelRead(Unpooled.copiedBuffer(msgStr.getBytes()));
        }
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
