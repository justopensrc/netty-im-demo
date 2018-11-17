package com.netease.music.im.protocol.request;

import com.netease.music.im.protocol.Packet;
import com.netease.music.im.protocol.cmd.Command;
import lombok.Data;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@Data
public class LoginRequestPacket extends Packet {

    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
