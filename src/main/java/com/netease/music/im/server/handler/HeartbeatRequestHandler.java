package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.HeartbeatRequestPacket;
import com.netease.music.im.protocol.response.HeartbeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@ChannelHandler.Sharable
@Slf4j
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequestPacket> {

    public static final HeartbeatRequestHandler INSTANCE = new HeartbeatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartbeatRequestPacket msg) throws Exception {
        ctx.writeAndFlush(new HeartbeatResponsePacket());
        log.info("heartbeat request");
    }
}
