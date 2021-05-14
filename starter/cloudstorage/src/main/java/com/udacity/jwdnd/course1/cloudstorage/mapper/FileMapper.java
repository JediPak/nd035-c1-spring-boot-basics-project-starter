package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FileMapper {

   @Select("SELECT * FROM FILES")
   public List <File> getFile();

   @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
   public User getFileById(Integer fileId);

   @Select("SELECT * FROM FILES WHERE userid=#{userid}")
   public User getFileByUserId(Integer userid);

   @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
           "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
   @Options(useGeneratedKeys = true, keyProperty = "fileId")
   public Integer createFile(File file);

   @Update("UPDATE FILES SET " +
           "filename=#{filename}, " +
           "contenttype=#{contenttype}, " +
           "filesize=#{filesize}, " +
           "userid=#{userid}, " +
           "filedata=#{filedata}, " +
           "WHERE fileId=#{fileId}")
   public Integer updateFile(File file);

   @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
   public void deleteFile(Integer fileId);
}
