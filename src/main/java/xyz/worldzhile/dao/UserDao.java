package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.User;

@Repository
public interface UserDao {

    @Insert("insert into user (uid,username,password,qq_email,name,birthday,phone,code,states) values (#{uid},#{username},#{password},#{qqEmail},#{name},#{birthday},#{phone},#{code},#{states}) ")
    void insert (User user);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states from user where username=#{username}")
    User findUserByUsername(String username);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states from user where code=#{code}")
    User findUserByCode(String code);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states from user where uid=#{uid}")
    User findUserByUid(String uid);

    @Update("update user set states=1 where code=#{code}")
    void active(String code);
}
