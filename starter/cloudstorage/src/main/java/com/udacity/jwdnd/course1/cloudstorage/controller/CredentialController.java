package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class CredentialController {

   private FileService fileService;
   private UserService userService;
   private NoteService noteService;
   private CredentialService credentialService;

   public CredentialController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
      this.fileService = fileService;
      this.userService = userService;
      this.noteService = noteService;
      this.credentialService = credentialService;
   }



   @PostMapping
   @RequestMapping("/credential")
   public String uploadCredential(@ModelAttribute("credential") Credential credentialInput, Authentication authentication, Model model) throws IOException {
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);

      String c_url = credentialInput.getUrl();
      String c_username = credentialInput.getUsername();
      String c_password = credentialInput.getPassword();
      credentialInput.setUserid(userid);

      //Credential credential = new Credential(null, filename, contenttype, filesize, userid, fis);
      System.out.println("point 1 credential: " +credentialInput.toString());
      System.out.println("credentialInput.getCredentialid(): "+credentialInput.getCredentialid());
      if(credentialInput.getCredentialid() == 0) {
         credentialService.createCredential(credentialInput);
         System.out.println("credential created: " + credentialInput.toString());
      } else {
         credentialService.updateCredential(credentialInput);
         System.out.println("credential updated: " + credentialInput.toString());
      }

      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));

      return "home";
   }
   /*
   @PostMapping
   @RequestMapping("/file-download")
   public String createFile(@ModelAttribute("file") MultipartFile file, Model model) throws IOException {
      String username = authentication.getName();
      InputStream fis = file.getInputStream();
      List<File> files =  fileService.getFileByUsername(username);
      return "home";
   }*/

   /*private MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String getChatPage(ChatForm chatForm, Model model) {
        model.addAttribute("chatMessages", this.messageService.getChatMessages());
        return "chat";
    }

    @PostMapping
    public String postChatMessage(Authentication authentication, ChatForm chatForm, Model model) {
        chatForm.setUsername(authentication.getName());
        this.messageService.addMessage(chatForm);
        chatForm.setMessageText("");
        model.addAttribute("chatMessages", this.messageService.getChatMessages());
        return "chat";
    }

    @ModelAttribute("allMessageTypes")
    public String[] allMessageTypes () {
        return new String[] { "Say", "Shout", "Whisper" };
    }*/
}
