package com.keikei.client.listen;

import com.keikei.common.domain.model.SendResult;

import java.util.List;

/**
 * 监听消息发送结果接口
 */
public  interface SendResultListen<T> {
    void businessPolling(List<T> sendResult);
}
