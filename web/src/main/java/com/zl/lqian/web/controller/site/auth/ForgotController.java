package com.zl.lqian.web.controller.site.auth;

import com.zl.lqian.base.data.Data;
import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.base.utils.MailHelper;
import com.zl.lqian.modules.user.data.UserVO;
import com.zl.lqian.modules.user.service.UserService;
import com.zl.lqian.modules.user.service.VerifyService;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.controller.site.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zl on 2015/8/14.
 */
@Controller
@RequestMapping("/forgot")
public class ForgotController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private MailHelper mailHelper;

    @RequestMapping("/apply")
    public String apply(String username, ModelMap model) {
        Data data = null;

        if (StringUtils.isNotBlank(username)) {
            UserVO user = userService.getByUsername(username);

            if (user != null) {
                String code = verifyService.generateCode(user.getId(), Consts.VERIFY_FORGOT, user.getEmail());
                Map<String, Object> context = new HashMap<>();
                context.put("userId", user.getId());
                context.put("code", code);
                context.put("type", Consts.VERIFY_FORGOT);

                sendEmail(Consts.EMAIL_TEMPLATE_FORGOT, user.getEmail(), "找回密码", context);

                data = Data.success("邮件发送成功", Data.NOOP);

                model.put("data", data);
                return view(Views.REGISTER_RESULT);
            } else {
                data = Data.failure("查无此用户");
            }
        }
        model.put("data", data);
        return view(Views.FORGOT_APPLY);
    }

    @RequestMapping("/reset")
    public String reset(Long userId, String token, String password, ModelMap model) {
        Data data;

        try {
            Assert.notNull(userId, "缺少必要的参数");
            Assert.hasLength(token, "缺少必要的参数");

            verifyService.verifyToken(userId, Consts.VERIFY_FORGOT, token);
            userService.updatePassword(userId, password);

            data = Data.success("恭喜您! 密码重置成功。");
            data.addLink("login", "去登陆");

        } catch (Exception e) {
            data = Data.failure(e.getMessage());
        }

        model.put("data", data);
        model.put("userId", userId);
        model.put("token", token);
        return view(Views.REGISTER_RESULT);
    }
}
