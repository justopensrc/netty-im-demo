package com.netease.music.im.protocol.response;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
public class HeartbeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
