/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.modules.blog.service;

import com.zl.lqian.modules.blog.entity.Channel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 栏目管理
 * 
 * @author zl
 *
 */
public interface ChannelService {

	List<Channel> findAll(int status);

	Map<Integer, Channel> findMapByIds(Collection<Integer> ids);

	Channel getById(int id);

	void update(Channel channel);

	void delete(int id);
}
