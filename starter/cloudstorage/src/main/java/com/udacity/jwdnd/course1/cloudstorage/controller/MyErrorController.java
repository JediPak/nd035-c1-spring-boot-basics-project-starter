package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//https://www.baeldung.com/spring-boot-custom-error-page
//"The limitation so far is that we can't run custom logic when errors occur.
// To achieve that, we have to create an error controller bean that'll replace the default one.
// For this, we have to create a class that implements the ErrorController interface and override its getErrorPath() method to return a custom path to call when an error occurred.
//
//However, starting version 2.3.x, Spring Boot has deprecated this method,
// and the property server.error.path should be used instead to specify the custom path.
//
//But since it's still a part of the ErrorController interface and hasn't been removed entirely,
// we'll need to override it or else the compiler will complain.
// To circumvent the problem here,  we're returning null as it is anyway going to be ignored:"
@Controller
public class MyErrorController implements ErrorController {

   //"This way the controller can handle calls to the /error path.
   //
   //In the handleError(), we return the custom error page we created earlier.
   // If we trigger a 404 error now, it's our custom page that will be displayed."
   @GetMapping("/error")
   public String handleError() {
      //do something like logging
      return "errorPage";

   }

   @Override
   public String getErrorPath() {
      return null;
   }
}