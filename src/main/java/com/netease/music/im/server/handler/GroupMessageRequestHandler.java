package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.GroupMessageRequestPacket;
import com.netease.music.im.protocol.response.GroupMessageResponsePacket;
import com.netease.music.im.session.Session;
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
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());
        if (channelGroup == null) {
            return;
        }

        Session session = SessionUtil.getSession(ctx.channel());
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUserName());
        responsePacket.setMsg(msg.getMsg());
        channelGroup.writeAndFlush(responsePacket);
    }
}
