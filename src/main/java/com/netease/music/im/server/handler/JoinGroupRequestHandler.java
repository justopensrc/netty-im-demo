package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.JoinGroupRequestPacket;
import com.netease.music.im.protocol.response.JoinGroupResponsePacket;
import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());

        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        if (channelGroup != null) {
            channelGroup.add(ctx.channel());
            responsePacket.setSuccess(true);
        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("Group doesn't exist");
        }
        ctx.writeAndFlush(responsePacket);
    }
}
