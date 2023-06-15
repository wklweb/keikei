package com.keikei.netty.nettys;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 维护userId 和 ctx
 */
public class UserChannelCtxMap {
    private static Map<Long, ChannelHandlerContext> channelMap = new ConcurrentHashMap();

    public static void addChannelCtx(Long userId,ChannelHandlerContext ctx){
        channelMap.put(userId,ctx);
    }
    public static void removeChannelCtx(Long userId){
        channelMap.remove(userId);
    }
    public static ChannelHandlerContext getChannelCtx(Long userId){
        return channelMap.get(userId);
    }
}
