/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.modules.blog.service.impl;

import com.zl.lqian.modules.blog.data.FeedsVO;
import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.modules.blog.dao.FeedsDao;
import com.zl.lqian.modules.blog.entity.Feeds;
import com.zl.lqian.modules.blog.service.FeedsService;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.modules.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zl
 *
 */
@Service
public class FeedsServiceImpl implements FeedsService {
	@Autowired
	private FeedsDao feedsDao;
	@Autowired
	private PostService postService;

	@Override
	@Transactional
	public int add(FeedsVO feeds) {
		Feeds po = new Feeds();
		BeanUtils.copyProperties(feeds, po);

		po.setCreated(new Date());

		// 给自己保存一条
		feedsDao.save(po);

		//TODO 可能有更好的解决办法
		// 派发给粉丝, 此处牺牲了空间, 提升查询速度
		return feedsDao.batchAdd(feeds);
	}

	@Override
	@Transactional
	public int deleteByAuthorId(long ownId, long authorId) {
		return feedsDao.deleteAllByOwnIdAndAuthorId(ownId, authorId);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FeedsVO> findUserFeeds(Pageable pageable, long ownId) {
		Page<Feeds> page = feedsDao.findAllByOwnIdOrderByIdDesc(pageable, ownId);

		List<FeedsVO> rets = new ArrayList<>();

		Set<Long> postIds = new HashSet<>();

		for (Feeds po : page.getContent()) {
			FeedsVO f = BeanMapUtils.copy(po);
			rets.add(f);

			postIds.add(f.getPostId());
		}

		// 加载文章
		Map<Long, PostVO> postMap = postService.findMapByIds(postIds);

		for (FeedsVO f : rets) {
			f.setPost(postMap.get(f.getPostId()));
		}
		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	@Transactional
	public void deleteByTarget(long postId) {
		feedsDao.deleteAllByPostId(postId);
	}

}
