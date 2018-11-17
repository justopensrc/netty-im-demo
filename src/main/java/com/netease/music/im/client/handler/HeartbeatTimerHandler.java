package com.netease.music.im.client.handler;

import com.netease.music.im.protocol.request.HeartbeatRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@ChannelHandler.Sharable
@Slf4j
public class HeartbeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int INTERVAL = 5;

    public static final HeartbeatTimerHandler INSTANCE = new HeartbeatTimerHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartbeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartbeat(ChannelHandlerContext ctx) {
        // 这里不能用 scheduleAtFixedRate(), 即使channel close了, 这个定时任务还在跑。
        ctx.executor().schedule(() -> {
            log.info("client heart beat");
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartbeatRequestPacket());
                scheduleSendHeartbeat(ctx);
            }
        }, INTERVAL, TimeUnit.SECONDS);
    }
}
