package com.zl.lqian.modules.authc.dao;

import com.zl.lqian.modules.authc.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author - zl
 * @create - 2018/5/18
 */
public interface RolePermissionDao extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {
    @Transactional
    int deleteByRoleId(long roleId);

    int deleteByPermissionId(long permissionId);

    List<RolePermission> findAllByRoleId(long roleId);
}
