package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class CredentialController {

   private EncryptionService encryptionService;
   private FileService fileService;
   private UserService userService;
   private NoteService noteService;
   private CredentialService credentialService;

   public CredentialController(EncryptionService encryptionService, FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
      this.encryptionService = encryptionService;
      this.fileService = fileService;
      this.userService = userService;
      this.noteService = noteService;
      this.credentialService = credentialService;
   }


   @GetMapping
   @RequestMapping("/credential/delete/{credentialid}")
   public String deleteCredential(@PathVariable("credentialid") Integer credentialid,
                                  @ModelAttribute("credential") Credential credential,
                                  Authentication authentication, Model model){
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);

      int deletedNum = 0;

      try {
         deletedNum= credentialService.deleteCredential(credentialid);
         if (deletedNum > 0) {
            model.addAttribute("successfulChange", true);
            model.addAttribute("successMsg", "Success: deleting credential was successful.");
            System.out.println("deletedNum: " + deletedNum);
         } else {
            model.addAttribute("successfulChange", false);
            model.addAttribute("errorMsg", "Error: deleting credential was not successful. (Error in DB)");
            System.out.println("deletedNum: " + deletedNum);
         }
      } catch (Exception e) {
         e.printStackTrace();
         model.addAttribute("successfulChange", false);
         model.addAttribute("errorMsg", "Error: deleting credential was not successful.");
         System.out.println("deletedNum: " + deletedNum);
      }


      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));
      model.addAttribute("encryptionService",encryptionService);

      return "result";
   }

   @PostMapping
   @RequestMapping("/credential")
   public String uploadCredential(@ModelAttribute("credential") Credential credential,
                                  Authentication authentication, Model model) throws IOException {
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);
      Integer status = -1;

      String c_url = credential.getUrl();
      String c_username = credential.getUsername();
      String c_password = credential.getPassword();

      //Credential credential = new Credential(null, filename, contenttype, filesize, userid, fis);
      System.out.println("credential: " +credential.toString());

      //creating new credential
      if(credential.getCredentialid() == 0) {
         try {
            //setting userid for the newly created note, since it shouldn't be defined yet
            credential.setUserid(userid);
            status = credentialService.createCredential(credential);
            if (status > 0) {
               System.out.println("credential created (successful): " + credential.toString());
               model.addAttribute("successfulChange", true);
               model.addAttribute("successMsg", "Success: creating credential was successful.");
            } else {
               System.out.println("credential created (successful--not status has 0 or neg number("+status+")): " + credential.toString());
               model.addAttribute("successfulChange", false);
               model.addAttribute("errorMsg", "Error: creating credential was not successful. (Error in DB)");
            }
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("credential created (error): " + credential.toString());
            model.addAttribute("successfulChange", false);
            model.addAttribute("errorMsg", "Error: creating credential was not successful.");

         }

      //updating existing credential
      } else {
         try {
            status = credentialService.updateCredential(credential);
            if (status > 0) {
               System.out.println("credential updated: " + credential.toString());
               model.addAttribute("successfulChange", true);
               model.addAttribute("successMsg", "Success: updating credential was successful.");
            } else {
               System.out.println("credential updated (successful--not status has 0 or neg number("+status+")): " + credential.toString());
               model.addAttribute("successfulChange", false);
               model.addAttribute("errorMsg", "Error: updating credential was not successful. (Error in DB)");
            }
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("credential updated: " + credential.toString());
            model.addAttribute("successfulChange", false);
            model.addAttribute("errorMsg", "Error: updating credential was not successful.");
         }
      }

      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));
      model.addAttribute("encryptionService",encryptionService);

      return "result";
   }

}
