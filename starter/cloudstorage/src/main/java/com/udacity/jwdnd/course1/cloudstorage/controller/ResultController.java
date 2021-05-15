package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {

   private FileService fileService;
   private UserService userService;
   private NoteService noteService;
   private CredentialService credentialService;

   public ResultController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
      this.fileService = fileService;
      this.userService = userService;
      this.noteService = noteService;
      this.credentialService = credentialService;
   }

   @GetMapping()
   public String getResult(Authentication authentication, Model model){
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);

      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));

      return "result";
   }

}
