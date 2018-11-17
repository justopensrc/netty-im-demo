package com.netease.music.im.client.handler;

import com.netease.music.im.protocol.*;
import com.netease.music.im.protocol.request.LoginRequestPacket;
import com.netease.music.im.protocol.response.LoginResponsePacket;
import com.netease.music.im.protocol.response.MessageResponsePacket;
import com.netease.music.im.utils.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername("client-fff");
        loginRequestPacket.setPassword("client-fff-pwd");
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, loginRequestPacket);
        ctx.writeAndFlush(byteBuf);
        log.info("sending login request...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                LoginUtils.markAsLogin(ctx.channel());
            }
            log.info("login response: {}", loginResponsePacket);
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            log.info("receive msg response, {}", messageResponsePacket.getMessage());
        }
    }
}
