package com.hjc.netty.test;

import com.alibaba.fastjson.JSONObject;
import com.hjc.netty.protocol.Header;
import com.hjc.netty.protocol.MessageType;
import com.hjc.netty.protocol.NettyMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Administrator
 * @date : 2018/5/14 0014 11:10
 * @description : FASTJSON测试
 */
public class JSONTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap();
        map.put("test", "张三");
        JSONObject jsonObject = new JSONObject();
        NettyMessage msg = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(123);
        header.setLength(456);
        header.setPriority((byte) 1);
        header.setAttachment(map);
        header.setType((byte) 2);
        msg.setHeader(header);
        System.out.println(JSONObject.toJSONString(msg));
        System.out.println(MessageType.LOGIN_RESP.value());
    }
}
