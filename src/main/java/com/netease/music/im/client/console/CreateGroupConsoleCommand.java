package com.netease.music.im.client.console;

import com.netease.music.im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.print("【拉人群聊】输入 userId 列表（英文逗号隔开）：");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIds(Arrays.asList(userIds.split(",")));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
