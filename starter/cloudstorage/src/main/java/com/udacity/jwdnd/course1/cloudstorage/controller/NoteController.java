package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class NoteController {

   private EncryptionService encryptionService;
   private FileService fileService;
   private UserService userService;
   private NoteService noteService;
   private CredentialService credentialService;

   public NoteController(EncryptionService encryptionService, FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
      this.encryptionService = encryptionService;
      this.fileService = fileService;
      this.userService = userService;
      this.noteService = noteService;
      this.credentialService = credentialService;
   }

   @GetMapping
   @RequestMapping("/note/delete/{noteid}")
   public String deleteNote(@PathVariable("noteid") Integer noteid, @ModelAttribute("note") Note note,
                            Authentication authentication, Model model){
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);

      int deletedNum = 0;

      try {
         deletedNum= noteService.deleteNote(noteid);
         if (deletedNum > 0) {
            model.addAttribute("successfulChange", true);
            model.addAttribute("successMsg", "Success: deleting note was successful.");
            System.out.println("deletedNum: " + deletedNum);
         } else {
            model.addAttribute("successfulChange", true);
            model.addAttribute("errorMsg", "Error: deleting note was not successful. (Error in DB)");
            System.out.println("deletedNum: " + deletedNum);
         }
      } catch (Exception e) {
         e.printStackTrace();
         model.addAttribute("successfulChange", false);
         model.addAttribute("errorMsg", "Error: deleting note was not successful.");
         System.out.println("deletedNum: " + deletedNum);
      }

      /*
      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));
      model.addAttribute("encryptionService",encryptionService);
      */
      return "result";
   }

   @PostMapping
   @RequestMapping("/note")
   public String uploadNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) throws IOException {
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);
      Integer status = -1;
      String n_notetitle = note.getNotetitle();
      String n_notedescription = note.getNotedescription();

      System.out.println("note.toString(): " +note.toString());
      System.out.println("note.getNoteid(): "+note.getNoteid());

      //creating new note
      if(note.getNoteid() == 0) {
         try {
            //setting userid for the newly created note, since it shouldnt be defined yet
            note.setUserid(userid);
            status = noteService.createNote(note);
            if (status > 0) {
               System.out.println("note created (successful): " + note.toString());
               model.addAttribute("successfulChange", true);
               model.addAttribute("successMsg", "Success: creating note was successful.");
            } else {
               System.out.println("note created (successful, but DB gave neg number ("+status+")): " + note.toString());
               model.addAttribute("successfulChange", false);
               model.addAttribute("errorMsg", "Error: creating note was not successful. (Error in DB)");
            }

         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("note created (error): " + note.toString());
            model.addAttribute("successfulChange", false);
            model.addAttribute("errorMsg", "Error: creating note was not successful.");
         }

      //updating existing credential
      } else {
         try {
            status = noteService.updateNote(note);
            if (status > 0) {
               System.out.println("note updated: " + note.toString());
               model.addAttribute("successfulChange", true);
               model.addAttribute("successMsg", "Success: updating note was successful.");
            } else {
               System.out.println("note created (successful, but DB gave neg number ("+status+")): " + note.toString());
               model.addAttribute("successfulChange", false);
               model.addAttribute("errorMsg", "Error: creating note was not successful. (Error in DB)");
            }
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("note updated: " + note.toString());
            model.addAttribute("successfulChange", false);
            model.addAttribute("errorMsg", "Error: updating note was not successful.");
         }
      }

      /*
      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));
      model.addAttribute("encryptionService",encryptionService);
      */
      return "result";
   }

}
