/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.core.event.handler;

import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.base.oauth.OauthOsc;
import com.zl.lqian.core.event.PostUpdateEvent;
import com.zl.lqian.modules.blog.data.FeedsVO;
import com.zl.lqian.modules.blog.service.FeedsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author zl on 2015/8/18.
 */
@Component
public class PostFeedsEventHandler implements ApplicationListener<PostUpdateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostFeedsEventHandler.class);
    private static final String AUTH_URL = "http://www.oschina.net/action/oauth2/authorize";


    @Autowired
    private FeedsService feedsService;

    @Async
    @Override
    public void onApplicationEvent(PostUpdateEvent event) {
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

                LOGGER.debug(MessageFormat.format("成功派发 {0} 条动态!", ret));
                break;
            case PostUpdateEvent.ACTION_DELETE:
                feedsService.deleteByTarget(event.getPostId());
                break;
        }

    }
}
