package com.netease.music.im.utils;

import com.netease.music.im.attr.Attributes;
import com.netease.music.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
public class LoginUtils {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }

    public static boolean valid(LoginRequestPacket loginRequestPacket) {
        if (loginRequestPacket.getPassword().equals("1234")) {
            return true;
        }
        return false;
    }
}
