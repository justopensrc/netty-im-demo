package com.netease.music.im.server.handler;

import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@ChannelHandler.Sharable
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 检验通过后，以后的消息都不需要再校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        /*if (SessionUtil.hasLogin(ctx.channel())) {
            log.info("client 已经登录过, 无需再进行校验, 移除AuthHandler");
        } else {
            log.info("client 没有验证通过, close连接");
        }*/
    }
}
