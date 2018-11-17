package com.netease.music.im.client;

import com.netease.music.im.client.console.ConsoleCommandManager;
import com.netease.music.im.client.console.LoginConsoleCommand;
import com.netease.music.im.client.handler.*;
import com.netease.music.im.codec.PacketCodecHandler;
import com.netease.music.im.codec.Splitter;
import com.netease.music.im.server.handler.IMIdleStateHandler;
import com.netease.music.im.utils.LoginUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author bjfanglong
 * @date 2018/11/15.
 */
@Slf4j
public class NettyClient {

    private static final String host = "localhost";
    private static final int port = 8080;
    private static final int MAX_RETRY = 3;

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(HeartbeatTimerHandler.INSTANCE);
                        // ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(LoginResponseHandler.INSTANCE);
                        ch.pipeline().addLast(LogoutResponseHandler.INSTANCE);
                        ch.pipeline().addLast(MessageResponseHandler.INSTANCE);
                        ch.pipeline().addLast(CreateGroupResponseHanlder.INSTANCE);
                        ch.pipeline().addLast(ListGroupMembersResponseHandler.INSTANCE);
                        ch.pipeline().addLast(JoinGroupResponseHanlder.INSTANCE);
                        ch.pipeline().addLast(GroupMessageResponseHandler.INSTANCE);
                        ch.pipeline().addLast(QuitGroupResponseHandler.INSTANCE);
                        // ch.pipeline().addLast(new PacketEncoder());

                        // ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, int retry) {
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("client connect [{}:{}] success", host, port);
                        Channel channel = ((ChannelFuture) future).channel();
                        startConsoleThread(channel);
                    } else if (retry == 0) {
                        log.warn("stop retry connect.");
                    } else {
                        log.error("client connect [{}:{}] failed", host, port);
                        // 第几次重连
                        int order = (MAX_RETRY - retry) + 1;
                        // 本次重连的间隔
                        int delay = 1 << order;
                        log.warn("{} 连接失败，第 {} 次重连……", new Date(), order);
                        bootstrap.config().group().schedule(
                                () -> connect(bootstrap, retry - 1), delay, TimeUnit.SECONDS);
                    }
                });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!channel.isActive()) {
                    log.error("连接关闭");
                    System.exit(1);
                    return;
                }
                
                if (!LoginUtils.hasLogin(channel)) {
                    loginConsoleCommand.exec(sc, channel);
                } else {
                    consoleCommandManager.exec(sc, channel);
                }
            }
        }).start();
    }

    private static void waitFroLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
