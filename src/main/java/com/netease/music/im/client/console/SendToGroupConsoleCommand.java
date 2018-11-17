package com.netease.music.im.client.console;

import com.netease.music.im.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入 groupId msg: ");
        String groupId = scanner.next();
        String msg = scanner.next();

        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setGroupId(groupId);
        requestPacket.setMsg(msg);
        channel.writeAndFlush(requestPacket);
    }
}
