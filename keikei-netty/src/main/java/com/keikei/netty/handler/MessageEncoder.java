package com.keikei.netty.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keikei.common.domain.model.IMsendInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<IMsendInfo> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMsendInfo sendInfo, List<Object> list) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String text = objectMapper.writeValueAsString(sendInfo);
        TextWebSocketFrame frame = new TextWebSocketFrame(text);
        list.add(frame);//实体对象类型转换为文本帧
    }
}
