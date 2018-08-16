package com.zl.lqian.web.controller.site.user;

import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.boot.mq.MQConstants;
import com.zl.lqian.boot.mq.RabbitMetaMessage;
import com.zl.lqian.modules.user.data.AccountProfile;
import com.zl.lqian.modules.user.service.FollowService;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.controller.mqservice.NotifyEvent;
import com.zl.lqian.web.controller.mqservice.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zl on 2015/8/18.
 */
@Controller
@RequestMapping("/user")
public class FollowController extends BaseController {
    @Autowired
    private FollowService followService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    RabbitSender rabbitSender;
    @RequestMapping("/follow")
    public @ResponseBody
    Data follow(Long id) {
        Data data = Data.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getSubject().getProfile();

                followService.follow(up.getId(), id);

                sendNotify(up.getId(), id);

                data = Data.success("关注成功!", Data.NOOP);
            } catch (Exception e) {
                data = Data.failure(e.getMessage());
            }
        }
        return data;
    }

    @RequestMapping("/unfollow")
    public @ResponseBody Data unfollow(Long id) {
        Data data = Data.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getSubject().getProfile();

                followService.unfollow(up.getId(), id);

                data = Data.success("取消成功!", Data.NOOP);
            } catch (Exception e) {
                data = Data.failure(e.getMessage());
            }
        }
        return data;
    }

    @RequestMapping("/check_follow")
    public @ResponseBody Data checkFollow(Long id) {
        Data data = Data.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getSubject().getProfile();

                boolean check = followService.checkFollow(up.getId(), id);

                data = Data.success("操作成功!", check);
            } catch (Exception e) {
                data = Data.failure(e.getMessage());
            }
        }
        return data;
    }

    /**
     * 发送关注通知
     * @param userId
     * @param followId
     */
    private void sendNotify(long userId, long followId) {
        RabbitMetaMessage message = new RabbitMetaMessage();
        //设置交换机
        message.setExchange(MQConstants.BUSINESS_EXCHANGE);
        //设置key
        message.setRoutingKey(MQConstants.NOTIFY_KEY);
        NotifyEvent event = new NotifyEvent();

        event.setToUserId(followId);
        event.setFromUserId(userId);
        event.setEvent(Consts.NOTIFY_EVENT_FOLLOW);
        message.setPayload(event);
        try {
            rabbitSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        //applicationContext.publishEvent(event);
    }
}
