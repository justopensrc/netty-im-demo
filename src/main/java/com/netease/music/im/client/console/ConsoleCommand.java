package com.netease.music.im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author bjfanglong
 * @date 2018/11/17.
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
