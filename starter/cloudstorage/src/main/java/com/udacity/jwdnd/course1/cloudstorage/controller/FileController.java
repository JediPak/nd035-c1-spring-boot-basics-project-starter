package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {

   private EncryptionService encryptionService;
   private FileService fileService;
   private UserService userService;
   private NoteService noteService;
   private CredentialService credentialService;

   public FileController(EncryptionService encryptionService, FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
      this.encryptionService = encryptionService;
      this.fileService = fileService;
      this.userService = userService;
      this.noteService = noteService;
      this.credentialService = credentialService;
   }

   @GetMapping
   @RequestMapping("/file/download/{fileid}")
   public ResponseEntity downloadNote(@PathVariable("fileid") Integer fileid,
                                                         @ModelAttribute("file") File file,
                                                         Authentication authentication, Model model){
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);
      ResponseEntity<?> fileToDownload = null;

      try {
         File fileFound = fileService.getFileById(fileid);

         //file was found
         if (fileFound != null) {
            System.out.println("file was found");

            fileToDownload =  ResponseEntity
                    //ok(): Represents the 200 status code that means that the response is fine
                    .ok()
                    //contentType(): Here we inform the type of file that we will be sending in the response
                    .contentType(MediaType.parseMediaType(fileFound.getContenttype()))
                    //header(): In the header we can put several options, but regarding the file we just need to establish how the content will be displayed to the user and due to your requirement the inline option was chosen so that the content if supported by the browser would open in a new tab.
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            //"inline; filename=\"" + fileFound.getFilename() + "\"")"
                            "attachment; filename=\"" + fileFound.getFilename() + "\"")
                    //body(): Here we pass all the data regarding the file that will be displayed and that can be downloaded by the user.
                    .body(new ByteArrayResource(fileFound.getFiledata()));

         //file was not found
         } else {
            System.out.println("Error: file was not found (Error in DB)");
            fileToDownload = ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error: file was not found (Error in DB)");
         }
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error: file was not found (Exception caught)");
         fileToDownload = ResponseEntity
                 .status(HttpStatus.FORBIDDEN)
                 .body("Error: file was not found (Exception caught)");
      }

      model.addAttribute("users", this.userService.getUserById(userid));
      model.addAttribute("files", this.fileService.getFilesByUserId(userid));
      model.addAttribute("notes", this.noteService.getNotesByUserId(userid));
      model.addAttribute("credentials", this.credentialService.getCredentialsByUserId(userid));
      model.addAttribute("encryptionService",encryptionService);

      return fileToDownload;
   }

   @GetMapping
   @RequestMapping("/file/delete/{fileid}")
   public String deleteFile(@PathVariable("fileid") Integer fileid, @ModelAttribute("file") File file,
                            Authentication authentication, Model model){
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);

      int deletedNum = 0;

      try {
         deletedNum= fileService.deleteFile(fileid);
         if (deletedNum > 0) {
            model.addAttribute("successfulChange", true);
            model.addAttribute("successMsg", "Success: deleting file was successful.");
            System.out.println("deletedNum: " + deletedNum);
         } else {
            model.addAttribute("successfulChange", true);
            model.addAttribute("errorMsg", "Error: deleting file was not successful. (Error in DB)");
            System.out.println("deletedNum: " + deletedNum);
         }
      } catch (Exception e) {
         e.printStackTrace();
         model.addAttribute("successfulChange", false);
         model.addAttribute("errorMsg", "Error: deleting file was not successful.");
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
   @RequestMapping("/file-upload")
   // https://gainjavaknowledge.medium.com/spring-boot-file-upload-example-e5c516e681a9
   // (^ link given by Udacity mentor)
   public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                            Authentication authentication, Model model) throws IOException {
      String username = authentication.getName();
      Integer userid = userService.getIdByUsername(username);
      Integer status = -1;
      boolean fileAlreadyExistWithUser;

      //checking if file was actually contains data
      if(fileUpload.isEmpty()) {
         model.addAttribute("successfulChange", true);
         model.addAttribute("successMsg", "No file selected to upload!");

      // file is not empty, now attempting to upload a new file
      } else {

         //uploading new file
         try {
            String filename = fileUpload.getOriginalFilename();
            System.out.println("fileUpload.getName(): "+fileUpload.getName());

            //checking if filename is already being used by this user
            fileAlreadyExistWithUser = fileService.doesFileNameAlreadyExist(filename, userid);
            if (fileAlreadyExistWithUser){
               System.out.println("filename ("+filename+") is already being used by this user ("+userid+")");
               model.addAttribute("successfulChange", false);
               model.addAttribute("errorMsg", "Error: uploading file was not successful. (Filename is already in use by you)");

            //filename is not being used by this user; proceeding to upload and save the file
            } else {
               //extracting fields from fileInput for file
               String contenttype = fileUpload.getContentType();
               String filesize = Long.toString(fileUpload.getSize());
               byte[] fis = fileUpload.getBytes();

               //creating new file object
               File file = new File(null, filename, contenttype, filesize, userid, fis);

               //setting userid for the newly created file, since it shouldn't be defined yet
               file.setUserid(userid);
               status = fileService.createFile(file);

               if (status > 0) {
                  System.out.println("file created (successful): " + file.toString());
                  model.addAttribute("successfulChange", true);
                  model.addAttribute("successMsg", "Success: uploading file was successful.");
               } else {
                  System.out.println("file created (successful, but DB gave neg number (" + status + ")): " + file.toString());
                  model.addAttribute("successfulChange", false);
                  model.addAttribute("errorMsg", "Error: uploading file was not successful. (Error in DB)");
               }
            }

         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("file uploaded (error)" );//+ file.toString());
            model.addAttribute("successfulChange", false);
            model.addAttribute("errorMsg", "Error: uploading file was not successful.");
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
