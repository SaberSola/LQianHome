/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.modules.user.dao;

import com.zl.lqian.modules.user.entity.OpenOauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 第三方开发授权登录
 * @author zl on 2015/8/12.
 */
public interface OpenOauthDao extends JpaRepository<OpenOauth, Long>, JpaSpecificationExecutor<OpenOauth> {
    OpenOauth findByAccessToken(String accessToken);

    OpenOauth findByOauthUserId(String oauthUserId);
    
    OpenOauth findByUserId(long userId);
}
