package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.QuitGroupRequestPacket;
import com.netease.music.im.protocol.response.QuitGroupResponsePacket;
import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        if (channelGroup == null) {
            responsePacket.setSuccess(false);
            responsePacket.setGroupId(msg.getGroupId());
            responsePacket.setReason(groupId +" doesn't exist.");
        } else {
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(msg.getGroupId());
            channelGroup.remove(ctx.channel());
        }
        ctx.writeAndFlush(responsePacket);
    }
}
