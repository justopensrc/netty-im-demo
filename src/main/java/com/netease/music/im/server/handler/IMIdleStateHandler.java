package com.netease.music.im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过 读 空闲检测假死
 *
 * @author bjfanglong
 * @date 2018/11/17.
 */
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READ_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        super(READ_IDLE_TIME, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.warn("读空闲 连接假死状态, close channel");
        ctx.channel().close();
    }
}
