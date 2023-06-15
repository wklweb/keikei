package com.keikei.client.listen;

import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.domain.model.SendResult;
import com.keikei.framework.annocation.SRListen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听分发器
 */
@Component
public class ListenDistribution {

    @Autowired
    private PrivateSendResultListen resultListen;

    @Autowired
    private List<SendResultListen> listenList;

    public void distribution(IMInfoType imInfoType, List<SendResult> sendResults) {
        for (SendResultListen sendResultListen : listenList) {
            SRListen srListen = AnnotationUtils.findAnnotation(sendResultListen.getClass(), SRListen.class);
            if ((srListen.type().equals(imInfoType) || srListen.type().equals(IMInfoType.All)) &&
                    srListen != null) {
                sendResultListen.businessPolling(sendResults);
            }
        }
    }
}
