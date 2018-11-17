package com.netease.music.im.protocol.request;

import com.netease.music.im.protocol.Packet;
import lombok.Data;

import static com.netease.music.im.protocol.cmd.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
