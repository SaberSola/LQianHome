package com.zl.lqian.modules.authc.service;

import com.zl.lqian.modules.authc.entity.Permission;
import com.zl.lqian.modules.authc.entity.RolePermission;

import java.util.List;
import java.util.Set;

/**
 * @author - zl
 * @create - 2018/5/18
 */
public interface RolePermissionService {
    List<Permission> findPermissions(long roleId);
    void deleteByRoleId(long roleId);
    void add(Set<RolePermission> rolePermissions);

}
