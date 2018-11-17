package com.netease.music.im.protocol;

import lombok.Data;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    public abstract Byte getCommand();
}
