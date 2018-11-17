package com.netease.music.im.server.handler;

import com.netease.music.im.protocol.request.CreateGroupRequestPacket;
import com.netease.music.im.protocol.response.CreateGroupResponsePacket;
import com.netease.music.im.utils.IDUtil;
import com.netease.music.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@ChannelHandler.Sharable
@Slf4j
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIds = msg.getUserIds();
        List<String> userNames = new ArrayList<>(userIds.size());

        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String uid : userIds) {
            Channel ch = SessionUtil.getChannel(uid);
            if (ch != null) {
                channelGroup.add(ch);
                userNames.add(SessionUtil.getSession(ch).getUserName());
            }
        }

        String groupId = IDUtil.randomId();
        SessionUtil.bindChannelGroup(groupId, channelGroup);
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNames(userNames);
        createGroupResponsePacket.setSuccess(true);

        channelGroup.writeAndFlush(createGroupResponsePacket);
        log.info("create group success, {}", createGroupResponsePacket);
    }
}
