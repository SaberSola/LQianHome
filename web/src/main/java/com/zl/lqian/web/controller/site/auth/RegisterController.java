/**
 * 
 */
package com.zl.lqian.web.controller.site.auth;

import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.base.utils.MailHelper;
import com.zl.lqian.modules.user.data.AccountProfile;
import com.zl.lqian.modules.user.data.UserVO;
import com.zl.lqian.modules.user.service.UserService;
import com.zl.lqian.modules.user.service.VerifyService;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author zl
 *
 */
@Controller
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private MailHelper mailHelper;
	@Autowired
	private ExecutorService executorService;
	
	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getSubject().getProfile();
		if (profile != null) {
			return "redirect:/home";
		}
		return view(Views.REGISTER);
	}
	
	@PostMapping("/register")
	public String register(UserVO post, ModelMap model) {
		Data data;
		String ret = view(Views.REGISTER);

		try {
			post.setAvatar(Consts.AVATAR);
			UserVO user = userService.register(post);

			String code = verifyService.generateCode(user.getId(), Consts.VERIFY_BIND, user.getEmail());
			Map<String, Object> context = new HashMap<>();
			context.put("userId", user.getId());
			context.put("code", code);
			context.put("type", Consts.VERIFY_BIND);

			sendEmail(Consts.EMAIL_TEMPLATE_BIND, user.getEmail(), "邮箱绑定验证", context);

			data = Data.success("恭喜您! 注册成功, 已经给您的邮箱发了验证码, 赶紧去完成邮箱绑定吧。", Data.NOOP);
			data.addLink("login", "先去登陆尝尝鲜");

			ret = view(Views.REGISTER_RESULT);
			
		} catch (Exception e) {
            model.addAttribute("post", post);
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

}