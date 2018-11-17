package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.*;
import com.netease.music.im.protocol.request.LoginRequestPacket;
import com.netease.music.im.protocol.request.MessageRequestPacket;
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
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (LoginUtils.valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("登录失败");
            }
            ByteBuf responseByteBuf = ctx.alloc().ioBuffer();
            PacketCodec.INSTANCE.encode(responseByteBuf, loginResponsePacket);
            ctx.writeAndFlush(responseByteBuf);
            log.info("sending login response...");
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            log.info("receive msg: {}", messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("server got the msg=" + messageRequestPacket.getMessage());
            ByteBuf responseByteBuf = ctx.alloc().ioBuffer();
            PacketCodec.INSTANCE.encode(responseByteBuf, messageResponsePacket);
            ctx.writeAndFlush(responseByteBuf);
            log.info("sending msg response");
        }
    }
    
}
