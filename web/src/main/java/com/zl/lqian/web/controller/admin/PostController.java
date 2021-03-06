/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.web.controller.admin;

import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.modules.blog.data.PostVO;
import com.zl.lqian.modules.blog.service.ChannelService;
import com.zl.lqian.modules.blog.service.PostService;
import com.zl.lqian.modules.user.data.AccountProfile;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.utils.OssUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zl
 *
 */
@Controller("adminPostController")
@RequestMapping("/admin/post")
public class PostController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/list")
	@RequiresPermissions("post:list")
	public String list(String title, ModelMap model, HttpServletRequest request) {
		long id = ServletRequestUtils.getLongParameter(request, "id", Consts.ZERO);
		int group = ServletRequestUtils.getIntParameter(request, "group", Consts.ZERO);

		Pageable pageable = wrapPageable();
		Page<PostVO> page = postService.paging4Admin(pageable, id, title, group);
		model.put("page", page);
		model.put("title", title);
		model.put("id", id);
		model.put("group", group);
		return "/admin/post/list";
	}
	
	/**
	 * 跳转到文章编辑方法
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String toUpdate(Long id, ModelMap model) {
		if (null != id && id > 0) {
			PostVO ret = postService.get(id);
			model.put("view", ret);
		}
		model.put("groups", channelService.findAll(Consts.IGNORE));
		return "/admin/post/update";
	}
	
	/**
	 * 更新文章方法
	 * @author LBB
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@RequiresPermissions("post:update")
	public String subUpdate(PostVO post, @RequestParam(value = "file", required=false) MultipartFile file) throws Exception {
		if (post != null) {
			//TODO 这俩需要改为oss上传
			String fileName = file.getOriginalFilename();
			/**
			 * 保存预览图片
			 */
			if (file != null && !file.isEmpty()) {
				String thumbnail = OssUtils.uploadImag(fileName,file);
				post.setThumbnail(thumbnail);
			}

			if (post.getId() > 0) {
				postService.update(post);
			} else {
				AccountProfile profile = getSubject().getProfile();
				post.setAuthorId(profile.getId());
				postService.post(post);
			}
		}
		return "redirect:/admin/post/list";
	}

	@RequestMapping("/featured")
	public @ResponseBody Data featured(Long id, HttpServletRequest request) {
		Data data = Data.failure("操作失败");
		int featured = ServletRequestUtils.getIntParameter(request, "featured", Consts.FEATURED_ACTIVE);
		if (id != null) {
			try {
				postService.updateFeatured(id, featured);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}

	@RequestMapping("/weight")
	public @ResponseBody Data weight(Long id, HttpServletRequest request) {
		Data data = Data.failure("操作失败");
		int weight = ServletRequestUtils.getIntParameter(request, "weight", Consts.FEATURED_ACTIVE);
		if (id != null) {
			try {
				postService.updateWeight(id, weight);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
	@RequestMapping("/delete")
	@RequiresPermissions("post:delete")
	public @ResponseBody Data delete(@RequestParam("id") List<Long> id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				postService.delete(id);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
}
