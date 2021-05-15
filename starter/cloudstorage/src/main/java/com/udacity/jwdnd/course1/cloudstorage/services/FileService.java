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

   public void createFile(File file){
      fileMapper.createFile(file);
   }

   public List<File> getFileByUsername(String username){
      Integer userid = userService.getIdByUsername(username);
      return getFilesByUserId(userid);
   }

   public List <File> getFilesByUserId(Integer userid){
      List <File> fileList = fileMapper.getFileByUserId(userid);
      if (fileList == null){
         return new ArrayList <File>();
      }
      return fileList;
   }
}
