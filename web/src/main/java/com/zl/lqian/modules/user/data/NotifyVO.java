package com.zl.lqian.modules.user.data;

import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.modules.user.entity.Notify;

/**
 * @author zl on 2015/8/31.
 */
public class NotifyVO extends Notify {
    // extend
    private UserVO from;
    private PostVO post;

    public UserVO getFrom() {
        return from;
    }

    public void setFrom(UserVO from) {
        this.from = from;
    }

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
