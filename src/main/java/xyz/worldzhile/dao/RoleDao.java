package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao {



    @Select(
            "SELECT roles.role_name " +
                    " FROM users  " +
                    " LEFT JOIN user_roles  " +
                    " ON users.uid=user_roles.urid_uid  " +
                    " Left join roles  " +
                    "on user_roles.urid_rid=roles.rid " +
                    "   where username=#{username} "
    )
    Set<String> findRolesByUsername(String username);
}
