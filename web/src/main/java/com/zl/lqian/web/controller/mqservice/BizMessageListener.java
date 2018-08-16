package com.zl.lqian.web.controller.mqservice;

import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.boot.mq.RabbitMetaMessage;

import com.zl.lqian.modules.blog.data.FeedsVO;
import com.zl.lqian.modules.blog.service.FeedsService;
import com.zl.lqian.modules.user.service.UserEventService;
import com.zl.lqian.web.utils.MapToBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * 这里接收业务消息
 */

@Component
public class BizMessageListener extends AbstractMessageListener {

    private Logger LOGGER = LoggerFactory.getLogger(BizMessageListener.class);

    @Autowired
    private FeedsService feedsService;
    @Autowired
    private UserEventService userEventService;

    @Override
    public void receiveMessage(Message message, MessageConverter messageConverter) {
        //贼几把奇怪 期初竟然是个LinkedHashMap
        PostUpdateEvent event = new PostUpdateEvent();
        LinkedHashMap<String,Object> hashMap =(LinkedHashMap)messageConverter.fromMessage(message);
        logger.info("get message success:"+hashMap.toString());
        MapToBean.transMap2Bean(hashMap,event);
        logger.info("get message success:"+event.toString());
        if (event == null) {
            return;
        }
        switch (event.getAction()) {
            case PostUpdateEvent.ACTION_PUBLISH:
                // 创建动态对象
                FeedsVO feeds = new FeedsVO();
                feeds.setType(Consts.FEEDS_TYPE_POST);
                feeds.setOwnId(event.getUserId());
                feeds.setPostId(event.getPostId());
                feeds.setAuthorId(event.getUserId());

                int ret = feedsService.add(feeds);
                //自动递增
                userEventService.identityPost(event.getUserId(), event.getPostId(), true);
                LOGGER.debug(MessageFormat.format("成功派发 {0} 条动态!", ret));
                break;
            case PostUpdateEvent.ACTION_DELETE:
                feedsService.deleteByTarget(event.getPostId());
                userEventService.identityPost(event.getUserId(), event.getPostId(), false);
                break;
        }
    }
}
