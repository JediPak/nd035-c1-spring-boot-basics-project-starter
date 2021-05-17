package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class HomeController {

   private EncryptionService encryptionService;

   private FileService fileService;
   private UserService userService;
   private NoteService noteService;
   private CredentialService credentialService;

   public HomeController(EncryptionService encryptionService, FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
      this.encryptionService = encryptionService;
      this.fileService = fileService;
      this.userService = userService;
      this.noteService = noteService;
      this.credentialService = credentialService;
   }

   @GetMapping("/logout")
   public String logoutView(){
      return "redirect:/login?logout";
   }

   @GetMapping
   @RequestMapping("/home")
   public String getHome(File file,
                         Note note,
                         Credential credential,
                         Authentication authentication,
                         Model model){

      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);

      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));
      model.addAttribute("encryptionService",encryptionService);

      return "home";
   }

}
