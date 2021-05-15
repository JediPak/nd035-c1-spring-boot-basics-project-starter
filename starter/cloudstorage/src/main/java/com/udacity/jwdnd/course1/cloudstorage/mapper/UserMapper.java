package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

   @Select("SELECT * FROM USERS")
   public List <User> getUser();

   @Select("SELECT * FROM USERS WHERE username=#{username}")
   public User getUserByUsername(String username);

   @Select("SELECT * FROM USERS WHERE userid=#{userid}")
   public User getUserById(Integer userid);

   @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) " +
           "VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
   @Options(useGeneratedKeys = true, keyProperty = "userid")
   public Integer createUser(User user);

   @Update("UPDATE USERS SET " +
           "username=#{username}, " +
           "salt=#{salt}, " +
           "password=#{password}" +
           "firstname=#{firstname}" +
           "lastname=#{lastname}" +
           "WHERE userid=#{userid}")
   public Integer updateUser(User user);

   @Delete("DELETE FROM USERS WHERE userid = #{userid}")
   public void deleteUser(Integer userid);
}
