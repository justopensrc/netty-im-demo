package com.netease.music.im.protocol.response;

import com.netease.music.im.protocol.Packet;
import lombok.Data;

import static com.netease.music.im.protocol.cmd.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
