package com.zl.lqian.web.controller.mqservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;


@Component
public class BizMessageListener extends AbstractMessageListener {

    private Logger LOGGER = LoggerFactory.getLogger(BizMessageListener.class);
    @Override
    public void receiveMessage(Message message, MessageConverter messageConverter) {
        Object bizObj = messageConverter.fromMessage(message);
        logger.info("get message success:"+bizObj.toString());
    }
}
