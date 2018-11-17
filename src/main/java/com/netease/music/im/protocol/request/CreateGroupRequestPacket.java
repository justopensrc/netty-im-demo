package com.netease.music.im.protocol.request;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;
import lombok.Data;

import java.util.List;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIds;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
