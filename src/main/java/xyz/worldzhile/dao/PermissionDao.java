package xyz.worldzhile.dao;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionDao {


    @Select("select  permission from roles_permissions where  role_name in(SELECT role_name FROM `user_roles` where username=#{username})")
    Set<String> findPermissionsByUsername(String username);
}
