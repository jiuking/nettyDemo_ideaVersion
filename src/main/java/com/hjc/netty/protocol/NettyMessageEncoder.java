package com.hjc.netty.protocol;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author : Hjc
 * @date : 2018/5/14 0014 11:16
 * @description : Netty消息编码类
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage>{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, List<Object> list) throws Exception {
        if (nettyMessage == null || nettyMessage.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        String content = JSONObject.toJSONString(nettyMessage);
        sendBuf.writeBytes(content.getBytes(CharsetUtil.UTF_8));
    }
}
