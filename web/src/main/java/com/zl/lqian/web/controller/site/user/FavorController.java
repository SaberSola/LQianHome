package com.zl.lqian.web.controller.site.user;

import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.modules.user.data.AccountProfile;
import com.zl.lqian.core.event.NotifyEvent;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zl on 2015/8/31.
 */
@Controller
@RequestMapping("/user")
public class FavorController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 喜欢文章
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/favor")
    public @ResponseBody
    Data favor(Long id, HttpServletRequest request) {
        Data data = Data.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getSubject().getProfile();
                postService.favor(up.getId(), id);

                sendNotify(up.getId(), id);

                data = Data.success("操作成功!", Data.NOOP);
            } catch (Exception e) {
                data = Data.failure(e.getMessage());
            }
        }
        return data;
    }

    /**
     * 取消喜欢文章
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/unfavor")
    public @ResponseBody Data unfavor(Long id, HttpServletRequest request) {
        Data data = Data.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getSubject().getProfile();
                postService.unfavor(up.getId(), id);
                data = Data.success("操作成功!", Data.NOOP);
            } catch (Exception e) {
                data = Data.failure(e.getMessage());
            }
        }
        return data;
    }

    /**
     * 发送通知
     * @param userId
     * @param postId
     */
    private void sendNotify(long userId, long postId) {
        NotifyEvent event = new NotifyEvent("NotifyEvent");
        event.setFromUserId(userId);
        event.setEvent(Consts.NOTIFY_EVENT_FAVOR_POST);
        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);
        applicationContext.publishEvent(event);
    }
}
