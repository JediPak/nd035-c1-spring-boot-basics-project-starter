package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

@Service
public class FileService {
   private final FileMapper fileMapper;

   public FileService(FileMapper fileMapper) {
      this.fileMapper = fileMapper;
   }
}
