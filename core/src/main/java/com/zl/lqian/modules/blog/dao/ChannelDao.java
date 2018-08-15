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

import com.zl.lqian.modules.blog.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

/**
 * @author zl
 *
 */
public interface ChannelDao extends JpaRepository<Channel, Integer>, JpaSpecificationExecutor<Channel> {
	List<Channel> findAllByStatus(int status);
	List<Channel> findAllByIdIn(Collection<Integer> id);
	Channel findByKey(String key);
}
