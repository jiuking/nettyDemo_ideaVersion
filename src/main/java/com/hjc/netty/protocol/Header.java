package com.hjc.netty.protocol;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 10:29
 * @description :
 */
public class Header {

    @Getter
    @Setter
    private int crcCode = 0xabef001;

    /**
     * 消息长度
     */
    @Getter
    @Setter
    private int length;

    /**
     * 回话ID
     */
    @Getter
    @Setter
    private long sessionId;

    /**
     * 消息类型
     */
    @Getter
    @Setter
    private byte type;

    /**
     * 消息优先级
     */
    @Getter
    @Setter
    private byte priority;

    /**
     * 附件
     */
    @Getter
    @Setter
    private Map<String, Object> attachment = new HashMap<>();

    @Override
    public String toString() {
        return "Header [crcCode=" + crcCode + ",length=" + length + ",sessionId=" + sessionId + ",type=" + type + ",priority=" + priority + ",attachment=" + attachment + "]";
    }
}
