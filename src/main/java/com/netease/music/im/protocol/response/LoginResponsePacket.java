package com.netease.music.im.protocol.response;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;
import lombok.Data;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
