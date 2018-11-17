package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.MessageRequestPacket;
import com.netease.music.im.protocol.response.MessageResponsePacket;
import com.netease.music.im.session.Session;
import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@ChannelHandler.Sharable
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        log.info("receive msg: {}", msg.getMessage());

        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(msg.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
            log.info("sending msg response");
        } else {
            log.warn("toUserId:{} isn't online.", msg.getToUserId());
        }
    }
}
