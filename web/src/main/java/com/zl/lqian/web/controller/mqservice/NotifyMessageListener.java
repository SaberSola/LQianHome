package com.zl.lqian.web.controller.mqservice;

import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.modules.user.data.NotifyVO;
import com.zl.lqian.modules.user.service.NotifyService;
import com.zl.lqian.web.utils.MapToBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class NotifyMessageListener extends AbstractMessageListener {

    private Logger LOGGER = LoggerFactory.getLogger(NotifyMessageListener.class);

    @Autowired
    private NotifyService notifyService;
    @Autowired
    private PostService postService;


    @Override
    public void receiveMessage(Message message, MessageConverter messageConverter) {

        NotifyEvent event = new NotifyEvent();
        LinkedHashMap<String,Object> hashMap =(LinkedHashMap)messageConverter.fromMessage(message);
        LOGGER.debug("get message success:"+hashMap.toString());
        MapToBean.transMap2Bean(hashMap,event);
        LOGGER.debug("get message success:"+event.toString());
        if (event == null) {
            return;
        }
        //处理业务需求
        NotifyVO nt = new NotifyVO();
        nt.setPostId(event.getPostId());
        nt.setFromId(event.getFromUserId());
        nt.setEvent(event.getEvent());

        switch (event.getEvent()) {
            case Consts.NOTIFY_EVENT_FAVOR_POST:
                PostVO p = postService.get(event.getPostId());
                nt.setOwnId(p.getAuthorId());
                break;
            case Consts.NOTIFY_EVENT_COMMENT:
            case Consts.NOTIFY_EVENT_COMMENT_REPLY:
                PostVO p2 = postService.get(event.getPostId());
                nt.setOwnId(p2.getAuthorId());
                // 自增评论数
                postService.identityComments(event.getPostId());
                break;
            default:
                nt.setOwnId(event.getToUserId());
        }
        notifyService.send(nt);
    }
}
