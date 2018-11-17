package com.netease.music.im.codec;

import com.netease.music.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@Slf4j
public class Splitter extends LengthFieldBasedFrameDecoder {


    public Splitter() {
        super(Integer.MAX_VALUE, 7, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUM) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
