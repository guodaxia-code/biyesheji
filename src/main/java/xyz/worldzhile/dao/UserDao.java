package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.domain.User;

import java.util.List;

@Repository
public interface UserDao {

    @Insert("insert into users (uid,username,password,qq_email,name,birthday,phone,code,states,createTime) values (#{uid},#{username},#{password},#{qqEmail},#{name},#{birthday},#{phone},#{code},#{states},#{createTime}) ")
    void insert (User user);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states,url from users where username=#{username}")
    @Results(id = "UserMap",value = {
            @Result(id = true,property = "uid",column = "uid"),
            @Result(property = "qqEmail",column = "qq_email"),
            @Result(property = "url",column = "url"),
            @Result(property = "roles" ,column="uid",many = @Many(select = "xyz.worldzhile.dao.RoleDao.findAllByUid",fetchType = FetchType.EAGER))
    })

    User findUserByUsername(String username);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states from users where code=#{code}")
    User findUserByCode(String code);

    @Select("select uid,username,password,qq_email,name,birthday,phone,code,states,url from users where uid=#{uid}")
    @ResultMap("UserMap")
    User findUserByUid(String uid);

    @Update("update users set states=1 where code=#{code}")
    void active(String code);

    @Update("update users set name=#{name},  birthday=#{birthday}, phone=#{phone},url=#{url}  where uid=#{uid}")
    void update(User userByUid);





    @Select("<script>"+"SELECT count(uid) FROM users  where 1=1 "
            +" and username like CONCAT('%',#{username},'%') "
            +"</script>"
    )
    int findCount(@Param("username")String username);

    /*layui 分页查询  weitiaojiao */
    @Select("<script>"+"SELECT * FROM users  where 1=1 "
            +"<if test='username!=null'> and username like CONCAT('%',#{username},'%')</if>"
            +"limit #{start},#{pageCount}"
            +"</script>"
    )
    @ResultMap("UserMap")
    List<User> findAllByLayuiByPage(@Param("start") int start, @Param("pageCount") Integer pageCount, @Param("username") String username);


    @Delete("delete from users where uid=#{uid}")
    void delete(String uid);



    @Select("select count(uid) from users")
    Integer findSum();


    /*某一时间之前的数量*/
    @Select("select count(uid) from users where createTime<  #{parse} ")
    Integer findCountBeforeTime(String format2);

    /*某一时间之前的数量*/
    @Select("select count(uid) from users where createTime<  #{end} and createTime > #{start} ")
    int findCountBetWin(@Param("start") String start, @Param("end")String end);
}
