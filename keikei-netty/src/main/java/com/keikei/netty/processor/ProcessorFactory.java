package com.keikei.netty.processor;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.utils.SpringUtils;

public class ProcessorFactory {

    public static MessageProcessor createProcessor(IMInfoType cmd){
        MessageProcessor  processor = null;
        switch (cmd){
            case LOGIN:
                processor = (MessageProcessor) SpringUtils.getBean(LoginProcessor.class);
                break;
            case HEART_BEAT:
                processor = (MessageProcessor) SpringUtils.getBean(HeartProcessor.class);
                break;
            case PRIVATE_MESSAGE:
                processor = (MessageProcessor)SpringUtils.getBean(PrivateMessageProcessor.class);
                break;
            case GROUP_MESSAGE:
                processor = (MessageProcessor)SpringUtils.getBean(GroupMessageProcessor.class);
                break;
            default:
                break;
        }
        return processor;
    }

}
