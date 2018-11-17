package com.netease.music.im.protocol.response;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;
import lombok.Data;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private boolean success;
    private String reason;
    
    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
