/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.modules.blog.data;

import com.zl.lqian.modules.blog.entity.Feeds;

/**
 * 订阅
 * @author zl
 *
 */
public class FeedsVO extends Feeds {
	private PostVO post;

	public PostVO getPost() {
		return post;
	}

	public void setPost(PostVO post) {
		this.post = post;
	}

}
