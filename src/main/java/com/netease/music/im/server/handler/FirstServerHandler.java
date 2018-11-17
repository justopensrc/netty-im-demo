package com.netease.music.im.server.handler;

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
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("receive: {}", byteBuf.toString(Charset.forName("utf-8")));

        // ctx.writeAndFlush(getByteBuf(ctx));
        log.info("sending...");
    }
    
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes("hello, I'm server.".getBytes(Charset.forName("utf-8")));
        return byteBuf;
    }
}
