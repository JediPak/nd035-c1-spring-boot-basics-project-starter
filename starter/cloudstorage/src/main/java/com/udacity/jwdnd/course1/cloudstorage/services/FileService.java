package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
   private FileMapper fileMapper;
   private UserService userService;

   public FileService(FileMapper fileMapper, UserService userService) {
      this.fileMapper = fileMapper;
      this.userService = userService;
   }

   public Integer createFile(File file){
      return fileMapper.createFile(file);
   }

   public Integer deleteFile(Integer fileid){
      return fileMapper.deleteFile(fileid);
   }

   public List<File> getFileByUsername(String username){
      Integer userid = userService.getIdByUsername(username);
      return getFilesByUserId(userid);
   }

   public File getFileById(Integer fileid){
      return fileMapper.getFileById(fileid);
   }

   public boolean doesFileNameAlreadyExist(String filename, Integer userid){
      return fileMapper.doesFileNameAlreadyExist(filename, userid) != null;
      //does exist --> true
   }

   public List <File> getFilesByUserId(Integer userid){
      List <File> fileList = fileMapper.getFileByUserId(userid);
      if (fileList == null){
         return new ArrayList <File>();
      }
      return fileList;
   }
}
