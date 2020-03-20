package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao {

    @Select("select role_name from user_roles where username=#{username}")
    Set<String> findRolesByUsername(String username);
}
