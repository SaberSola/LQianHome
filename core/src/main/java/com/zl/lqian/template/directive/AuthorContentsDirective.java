/**
 *
 */
package com.zl.lqian.template.directive;

import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.template.DirectiveHandler;
import com.zl.lqian.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取文章列表
 *
 * @author langhsu
 *
 */
@Component
public class AuthorContentsDirective extends TemplateDirective {
    @Autowired
	private PostService postService;

	@Override
	public String getName() {
		return "author_contents";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int pn = handler.getInteger("pn", 1);
        long uid = handler.getInteger("uid", 0);

        Pageable pageable = new PageRequest(pn - 1, 10);
        Page<PostVO> result = postService.pagingByAuthorId(pageable, uid);

        handler.put(RESULTS, result).render();
    }

}
