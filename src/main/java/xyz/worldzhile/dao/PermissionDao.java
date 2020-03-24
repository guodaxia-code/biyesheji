package xyz.worldzhile.dao;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionDao {



    @Select("SELECT permissions.permission\n" +
            "FROM users\n" +
            "LEFT JOIN user_roles \n" +
            "ON users.uid=user_roles.urid_uid\n" +
            "Left join roles \n" +
            "on user_roles.urid_rid=roles.rid\n" +
            "Left join roles_permissions\n" +
            "ON roles.rid=roles_permissions.rp_rid\n" +
            "Left join permissions\n" +
            "on roles_permissions.rp_pid=permissions.perid\n" +
            "where username=#{username} ")
    Set<String> findPermissionsByUsername(String username);
}
