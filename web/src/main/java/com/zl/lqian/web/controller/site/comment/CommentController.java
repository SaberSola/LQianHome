/**
 * 
 */
package com.zl.lqian.web.controller.site.comment;

import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.boot.mq.MQConstants;
import com.zl.lqian.boot.mq.RabbitMetaMessage;
import com.zl.lqian.modules.user.data.AccountProfile;
import com.zl.lqian.modules.blog.data.CommentVO;
import com.zl.lqian.modules.blog.service.CommentService;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.controller.mqservice.NotifyEvent;
import com.zl.lqian.web.controller.mqservice.RabbitSender;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zl
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private RabbitSender rabbitSender;

	@RequestMapping("/list/{toId}")
	public @ResponseBody Page<CommentVO> view(@PathVariable Long toId) {
		Pageable pageable = wrapPageable();
		Page<CommentVO> page = commentService.paging(pageable, toId);
		return page;
	}
	
	@RequestMapping("/submit")
	public @ResponseBody
	Data post(Long toId, String text, HttpServletRequest request) {
		Data data = Data.failure("操作失败");
		
		long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);
		
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			data = Data.failure("请先登录在进行操作");
			
			return data;
		}
		if (toId > 0 && StringUtils.isNotEmpty(text)) {
			AccountProfile up = getSubject().getProfile();
			
			CommentVO c = new CommentVO();
			c.setToId(toId);
			c.setContent(HtmlUtils.htmlEscape(text));
			c.setAuthorId(up.getId());
			
			c.setPid(pid);
			
			commentService.post(c);

            if(toId != up.getId()) {
			    sendNotify(up.getId(), toId, pid);
            }
			
			data = Data.success("发表成功!", Data.NOOP);
		}
		return data;
	}

	@RequestMapping("/delete")
	public @ResponseBody Data delete(Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			AccountProfile up = getSubject().getProfile();
			try {
				commentService.delete(id, up.getId());
				data = Data.success("操作成功", Data.NOOP);
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
	private void sendNotify(long userId, long postId, long pid) {

		//TODO 此处用mq做
		RabbitMetaMessage message = new RabbitMetaMessage();
		message.setExchange(MQConstants.BUSINESS_EXCHANGE);
		message.setRoutingKey(MQConstants.NOTIFY_KEY);

		NotifyEvent event = new NotifyEvent();
		event.setFromUserId(userId);

		if (pid > 0) {
			event.setEvent(Consts.NOTIFY_EVENT_COMMENT_REPLY);
		} else {
			event.setEvent(Consts.NOTIFY_EVENT_COMMENT);
		}
		// 此处不知道文章作者, 让通知事件系统补全
		event.setPostId(postId);
		message.setPayload(event);
		try {
			rabbitSender.send(message);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}