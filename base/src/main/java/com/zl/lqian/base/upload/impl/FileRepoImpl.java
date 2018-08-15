/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.base.upload.impl;

import org.springframework.stereotype.Service;

/**
 * @author zl
 *
 */
@Service
public class FileRepoImpl extends AbstractFileRepo {
	@Override
	public String getRoot() {
		return appContext.getRoot();
	}
}
