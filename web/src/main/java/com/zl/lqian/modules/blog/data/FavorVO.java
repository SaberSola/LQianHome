package com.zl.lqian.modules.blog.data;

import com.zl.lqian.modules.blog.entity.Favor;

/**
 * @author zl on 2015/8/31.
 */
public class FavorVO extends Favor {
    // extend
    private PostVO post;

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
