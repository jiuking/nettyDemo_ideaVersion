package com.hjc.netty.protocol;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 16:23
 */
public enum MessageType {

    LOGIN_REQ((byte)1),LOGIN_RESP((byte) 2);

    private byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }
}
