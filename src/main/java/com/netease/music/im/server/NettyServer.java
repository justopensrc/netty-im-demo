package com.netease.music.im.server;

import com.netease.music.im.codec.PacketCodecHandler;
import com.netease.music.im.codec.Splitter;
import com.netease.music.im.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bjfanglong
 * @date 2018/11/15.
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(10);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                // 针对NioServerSocketChannel, 服务端启动
                .attr(AttributeKey.newInstance("serverName"), "im-server")
                .handler(new ChannelInitializer<NioServerSocketChannel>() {

                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        log.info("服务启动中.....");
                    }
                })
                // 针对客户端的每条连接
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 开启TCP底层心跳机制
                .childOption(ChannelOption.TCP_NODELAY, true)  // 开启高实时性, 有数据就立刻发送
                .childAttr(AttributeKey.newInstance("clientKey"), "1.0")
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        // ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartbeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                        // ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(LogoutRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(new PacketEncoder());

                        // ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        bind(serverBootstrap, 8080);
    }

    /**
     * 递增尝试绑定端口
     *
     * @param serverBootstrap
     * @param port
     */
    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(8080).addListener(future -> {
            if (future.isSuccess()) {
                log.info("server bind [{}] success", port);
            } else {
                log.error("server bind [{}] failed", port);
                bind(serverBootstrap, port);
            }
        });
    }
}
