/**
 *
 */
package com.zl.lqian.template.directive;

import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.modules.blog.entity.Channel;
import com.zl.lqian.modules.blog.service.ChannelService;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.template.DirectiveHandler;
import com.zl.lqian.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文章内容查询
 *
 * 示例：
 * 	请求：http://mtons.com/index?ord=newest&pn=2
 *  使用：@contents group=x pn=pn ord=ord
 *  s首页内容
 * @author zl
 *
 */
@Component
public class ContentsDirective extends TemplateDirective {
	@Autowired
    private PostService postService;
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer pn = handler.getInteger("pn", 1);
        Integer channelId = handler.getInteger("channelId", 0);
        String order = handler.getString("order", Consts.order.NEWEST);
        Integer size = handler.getInteger("size", 16);

        Set<Integer> excludeChannelIds = new HashSet<>();

        if (channelId <= 0) {
            List<Channel> channels = channelService.findAll(Consts.STATUS_CLOSED);
            if (channels != null) {
                channels.forEach((c) -> excludeChannelIds.add(c.getId()));
            }
        }

        Pageable pageable = new PageRequest(pn - 1, size);
        Page<PostVO> result = postService.paging(pageable, channelId, excludeChannelIds, order);

        handler.put(RESULTS, result).render();
    }
}
