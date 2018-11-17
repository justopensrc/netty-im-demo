package com.netease.music.im.protocol.request;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
public class HeartbeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
