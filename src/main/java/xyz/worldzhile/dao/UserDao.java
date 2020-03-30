package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.domain.User;

import java.util.List;

@Repository
public interface UserDao {

    @Insert("insert into users (uid,username,password,qq_email,name,birthday,phone,code,states) values (#{uid},#{username},#{password},#{qqEmail},#{name},#{birthday},#{phone},#{code},#{states}) ")
    void insert (User user);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states,url from users where username=#{username}")
    @Results(id = "UserMap",value = {
            @Result(id = true,property = "uid",column = "uid"),
            @Result(property = "qqEmail",column = "qq_email"),
            @Result(property = "url",column = "url"),
    })

    User findUserByUsername(String username);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states from users where code=#{code}")
    User findUserByCode(String code);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states,url from users where uid=#{uid}")
    User findUserByUid(String uid);

    @Update("update users set states=1 where code=#{code}")
    void active(String code);

    @Update("update users set name=#{name},  birthday=#{birthday}, phone=#{phone},url=#{url}  where uid=#{uid}")
    void update(User userByUid);





    @Select("<script>"+"SELECT count(uid) FROM users  where 1=1 "
//            +" and pname like CONCAT('%',#{panme},'%') "
            +"</script>"
    )
    int findCount();

    /*layui 分页查询  weitiaojiao */
    @Select("<script>"+"SELECT * FROM users  where 1=1 "
//            +"<if test='pname!=null'> and pname like CONCAT('%',#{pname},'%')</if>"
            +"limit #{start},#{pageCount}"
            +"</script>"
    )
    @ResultMap("UserMap")
    List<User> findAllByLayuiByPage(@Param("start") int start, @Param("pageCount") Integer pageCount, @Param("pname") String pname);





}
