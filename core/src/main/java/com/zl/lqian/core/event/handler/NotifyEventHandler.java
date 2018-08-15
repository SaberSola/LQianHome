/*
package com.zl.lqian.core.event.handler;

import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.modules.user.data.NotifyVO;
import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.core.event.NotifyEvent;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.modules.user.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

*/
/**
 * @author zl on 2015/8/31.
 *//*

@Component
public class NotifyEventHandler implements ApplicationListener<NotifyEvent> {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private PostService postService;

    @Async
    @Override
    public void onApplicationEvent(NotifyEvent event) {
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
*/
