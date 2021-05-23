package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

   @Select("SELECT * FROM CREDENTIALS")
   public List <Credential> getCredential();

   @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialid}")
   public Credential getCredentialById(Integer credentialid);

   @Select("SELECT * FROM CREDENTIALS WHERE " +
           "username=#{username} AND userid=#{userid}")
   public List<Credential> usernameAvailable(String username);

   @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
   public List <Credential> getCredentialByUserId(Integer userid);

   @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
           "VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
   @Options(useGeneratedKeys = true, keyProperty = "credentialid")
   public Integer createCredential(Credential credential);

   //UPDATE table_name
   //SET column1 = value1, column2 = value2, ...
   //WHERE condition;
   @Update("UPDATE CREDENTIALS SET " +
           "url=#{url}, " +
           "username=#{username}, " +
           "password=#{password}" +
           "WHERE credentialid=#{credentialid}")
   public Integer updateCredential(Credential credential);

   @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
   public Integer deleteCredential(Integer credentialid);
}
