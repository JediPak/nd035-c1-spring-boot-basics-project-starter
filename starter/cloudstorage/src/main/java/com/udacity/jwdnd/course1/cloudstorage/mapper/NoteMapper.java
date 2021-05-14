package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NoteMapper {

   @Select("SELECT * FROM NOTES")
   public List <Note> getNote();

   @Select("SELECT * FROM NOTES WHERE noteid=#{noteid}")
   public User getNoteById(Integer noteid);

   @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
   public User getNoteByUserId(Integer userid);

   @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
           "VALUES (#{notetitle}, #{notedescription}, #{userid})")
   @Options(useGeneratedKeys = true, keyProperty = "noteid")
   public Integer createUser(Note note);

   @Update("UPDATE NOTES SET " +
           "notetitle=#{notetitle}, " +
           "notedescription=#{notedescription}, " +
           "userid=#{userid}, " +
           "WHERE noteid=#{noteid}")
   public Integer updateNote(Note note);

   @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
   public void deleteNote(Integer noteid);
}
