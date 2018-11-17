package com.netease.music.im.attr;

import com.netease.music.im.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
