package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

   private UserService userService;

   public SignupController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping()
   public String getSignup(){
      return "signup";
   }

   @PostMapping()
   //https://knowledge.udacity.com/questions/531823 -- information on why
   //@ModelAttribute doesnt have ("user") as part of the annotation
   public String singupUser(@ModelAttribute User user, Model model){
      String signupError = null;
      int userid = -1;

      if (!userService.isUsernameAvailable(user.getUsername())){
         signupError = "The username already exists.";
      }
      if (signupError == null){

         userid = userService.createUser(user);
         if (userid < 0){
            signupError = "There was an error signing you up.";
         }
      }

      if (signupError == null){
         model.addAttribute("signupSuccess", true);
         model.addAttribute("users", this.userService.getUserById(userid));
      }
      else{
         model.addAttribute("signupError", signupError);
      }

      //redirecting to different page after signing up
      if (signupError == null) {

         model.addAttribute("signupSuccess", true);

         return "signupredirect";

      } else {

         model.addAttribute("signupError", signupError);

         return "signup";
      }
      //return "signup";
   }
}
