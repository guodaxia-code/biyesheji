package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.Role;

import java.util.List;
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




    @Select("select users.*,roles.* from user_roles \n" +
            "LEFT JOIN users\n" +
            "ON users.uid=user_roles.urid_uid\n" +
            "LEFT JOIN roles\n" +
            "ON user_roles.urid_rid=roles.rid\n" +
            "where uid=#{uid} ")
    @ResultMap("roleMapper")
    List<Role> findAllByUid(String uid);


//    查找一个角色
    @Select("select * from roles where rid=#{rid}")
    @Results(id="roleMapper")
    Role findByRid(String rid);


}
