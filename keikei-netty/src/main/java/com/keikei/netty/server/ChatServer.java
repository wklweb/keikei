package com.keikei.netty.server;
import com.keikei.netty.nettys.IMServer;
import com.keikei.netty.handler.ChatServerInitializer;
import com.keikei.netty.task.PullMessageTask;
import com.keikei.netty.task.PullPrivateMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "websocket", value = "enable", havingValue = "true")
public class ChatServer implements IMServer {

    private static EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static EventLoopGroup workGroup = new NioEventLoopGroup();
    private volatile boolean ready = false;
    @Value("${websocket.port}")
    private int port;
    @Autowired
    private PullPrivateMessage pullPrivateMessage;
    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void start() throws InterruptedException {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 5)
                    // 表示连接保活，相当于心跳机制，默认为7200s
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).syncUninterruptibly();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("服务器正在启动...");
                    }
                    if (channelFuture.isDone()) {
                        ready = true;
                        System.out.println("服务器启动完成...");
                    }
                }
            });
            ChannelFuture channelFuture = future.channel().closeFuture().sync();
        }
        catch (Exception e){
            log.error(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void close() {
        ready = false;
        if(!bossGroup.isShutdown()&&bossGroup!=null&&!bossGroup.isShuttingDown()){
            bossGroup.shutdownGracefully();
            log.info("bossGroup关闭成功");
        }
        if(!workGroup.isShutdown()&&workGroup!=null&&!workGroup.isShuttingDown()){
            workGroup.shutdownGracefully();
            log.info("WorkGroup关闭成功");
        }
    }



    public static EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public static EventLoopGroup getWorkGroup() {
        return workGroup;
    }
}
