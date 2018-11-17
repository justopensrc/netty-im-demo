package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.ListGroupMembersRequestPacket;
import com.netease.music.im.protocol.response.ListGroupMembersResponsePacket;
import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@ChannelHandler.Sharable
@Slf4j
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        List<String> members = new ArrayList<>();
        if (channelGroup != null) {
            for (Channel ch : channelGroup) {
                members.add(SessionUtil.getSession(ch).getUserName());
            }
        }

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setMembers(members);
        ctx.writeAndFlush(responsePacket);
        log.info("sending listGroupMembers...");
    }
}
