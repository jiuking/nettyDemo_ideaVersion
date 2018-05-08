package com.hjc.netty.aio;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * @author Hjc
 * @date 2018-05-08
 */
public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel result) {
        if (channel == null) {
            this.channel = result;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        try {
            String req = new String(bytes, "UTF-8");
            System.out.println("The time server receive order : " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date().toString() : "Bad Request";
            doWrite(currentTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String currentTime) {
        if (currentTime != null) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        channel.write(attachment, attachment, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
