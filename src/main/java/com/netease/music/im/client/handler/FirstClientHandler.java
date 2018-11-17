package com.netease.music.im.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author bjfanglong
 * @date 2018/11/15.
 */
@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // ctx.channel().writeAndFlush(getByteBuf(ctx));
        log.info("sending...");


        for (int i = 0; i < 200; i++) {
            // MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
            // messageRequestPacket.setMessage(i + ": 测试沾包半包的问题，哈哈哈哈或或!");
            ctx.channel().writeAndFlush(getByteBuf(ctx));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("receive: {}", ((ByteBuf) msg).toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes("hello, I'm client.".getBytes(Charset.forName("utf-8")));
        return byteBuf;
    }
}
