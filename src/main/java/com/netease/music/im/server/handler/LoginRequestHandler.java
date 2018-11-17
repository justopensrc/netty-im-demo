package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.LoginRequestPacket;
import com.netease.music.im.protocol.response.LoginResponsePacket;
import com.netease.music.im.session.Session;
import com.netease.music.im.utils.LoginUtils;
import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@ChannelHandler.Sharable
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (LoginUtils.valid(msg)) {
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setSuccess(true);
            SessionUtil.bindSession(new Session(userId, msg.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("登录失败");
        }
        ctx.writeAndFlush(loginResponsePacket);
        log.info("sending login response...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
