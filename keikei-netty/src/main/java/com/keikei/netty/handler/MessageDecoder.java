package com.keikei.netty.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keikei.common.domain.model.IMsendInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class MessageDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame, List<Object> list) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        IMsendInfo sendInfo = objectMapper.readValue(textWebSocketFrame.text(), IMsendInfo.class);
        list.add(sendInfo);
    }
}
