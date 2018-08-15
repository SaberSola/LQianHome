/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.modules.blog.dao;

import com.zl.lqian.modules.blog.dao.custom.FeedsDaoCustom;
import com.zl.lqian.modules.blog.entity.Feeds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zl
 *
 */
public interface FeedsDao extends JpaRepository<Feeds, Long>, JpaSpecificationExecutor<Feeds>, FeedsDaoCustom {
	Page<Feeds> findAllByOwnIdOrderByIdDesc(Pageable pageable, long ownId);
	int deleteAllByOwnIdAndAuthorId(long ownId, long authorId);
	void deleteAllByPostId(long postId);
}
