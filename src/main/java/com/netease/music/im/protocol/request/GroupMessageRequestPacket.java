package com.netease.music.im.protocol.request;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;
import lombok.Data;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@Data
public class GroupMessageRequestPacket extends Packet {

    private String groupId;
    private String msg;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
