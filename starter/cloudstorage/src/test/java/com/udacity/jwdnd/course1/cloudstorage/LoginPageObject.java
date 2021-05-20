package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {

   @FindBy(id = "inputUsername")
   private WebElement usernameField;

   @FindBy(id = "inputPassword")
   private WebElement passwordField;

   @FindBy(id = "login-btn")
   private WebElement login;

   @FindBy(id = "signup-link")
   private WebElement signUp;

   @FindBy(id = "error-msg")
   private WebElement loginFailedmsg;

   @FindBy(className = "display-5")
   private WebElement title;

   private JavascriptExecutor jE;

   public LoginPageObject(WebDriver d){
      jE = (JavascriptExecutor) d;
      PageFactory.initElements(d, this);
   }

   public void login(String username, String password){
      /*usernameField.sendKeys(username);
      passwordField.sendKeys(password);
      submit.click();
       */
      jE.executeScript("arguments[0].value='" + username + "';", usernameField);
      jE.executeScript("arguments[0].value='" + password + "';", passwordField);
      jE.executeScript("arguments[0].click();", login);
   }

   public void signup(){
      /*signUp.click();
       */
      jE.executeScript("arguments[0].click();", signUp);
   }

   public boolean wrongLogin(){
      return loginFailedmsg.isDisplayed();
   }

   public boolean atLoginPage(){
      return title.getText().equals("Login");
   }

}
