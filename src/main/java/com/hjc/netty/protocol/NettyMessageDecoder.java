package com.hjc.netty.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 14:51
 * @description : Netty消息解码类
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        byte[] bytes = new byte[in.capacity()];
        in.writeBytes(bytes);
        String content = new String(bytes, CharsetUtil.UTF_8);
        NettyMessage message = JSON.parseObject(content, NettyMessage.class);
        return message;
    }
}
