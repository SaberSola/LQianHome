/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.web.controller.site.user;

import com.zl.lqian.base.context.AppContext;
import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.utils.FilePathUtils;
import com.zl.lqian.base.utils.ImageUtils;
import com.zl.lqian.modules.user.data.AccountProfile;
import com.zl.lqian.modules.user.service.UserService;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;

/**
 * @author zl
 *
 */
@Controller
@RequestMapping("/user")
public class AvatarController extends BaseController {
	@Autowired
	private AppContext appContext;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public String view() {
		return view(Views.USER_AVATAR);
	}
	
	@RequestMapping(value = "/avatar", method = RequestMethod.POST)
	public String post(String path, Float x, Float y, Float width, Float height, ModelMap model) {
		AccountProfile profile = getSubject().getProfile();
		
		if (StringUtils.isEmpty(path)) {
			model.put("data", Data.failure("请选择图片"));
			return view(Views.USER_AVATAR);
		}
			try {
				AccountProfile user = userService.updateAvatar(profile.getId(),path);
				putProfile(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "redirect:/user/profile";
	}
}
