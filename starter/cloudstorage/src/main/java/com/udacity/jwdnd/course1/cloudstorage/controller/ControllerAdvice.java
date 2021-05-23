package com.udacity.jwdnd.course1.cloudstorage.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
//   private final static String errorPage = "error";
//   @ExceptionHandler(MaxUploadSizeExceededException.class)
//   public ModelAndView handleMaxSizeException(
//           MaxUploadSizeExceededException exc,
//           HttpServletRequest request,
//           HttpServletResponse response) {
//      return new ModelAndView(errorPage);
//   }

   @ExceptionHandler(MaxUploadSizeExceededException.class)
   public String handleMaxSizeException(Model model) {

      model.addAttribute("successfulChange", false);
      model.addAttribute("errorMsg", "Error: uploading file was not successful. (File size is too large, limit is 10MB)");

      //return "errorPage";
      return "result";
   }
}