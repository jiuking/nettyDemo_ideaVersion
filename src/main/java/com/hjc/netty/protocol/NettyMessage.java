package com.hjc.netty.protocol;


import lombok.Getter;
import lombok.Setter;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 10:18
 * @description : Netty协议栈，Netty消息定义
 */
public final class NettyMessage {


    /**
     * 消息头
     */
    @Getter
    @Setter
    private Header header;

    /**
     * 消息体
     */
    @Getter
    @Setter
    private Object body;

    @Override
    public String toString() {
        return "NeetyMessage [header="+header+"]";
    }
}
