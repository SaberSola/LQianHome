package com.zl.lqian.web.controller.admin;

import com.zl.lqian.modules.authc.data.PermissionTree;
import com.zl.lqian.modules.authc.service.PermissionService;
import com.zl.lqian.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author - zl
 * @create - 2018/5/18
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/tree")
    @ResponseBody
    public List<PermissionTree> tree() {
        return permissionService.tree();
    }
}
