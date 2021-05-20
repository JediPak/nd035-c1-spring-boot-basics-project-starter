package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPageObject {

   @FindBy(id = "inputFirstName")
   private WebElement firstNameField;

   @FindBy(id = "inputLastName")
   private WebElement lastNameField;

   @FindBy(id = "inputUsername")
   private WebElement usernameField;

   @FindBy(id = "inputPassword")
   private WebElement passwordField;

   @FindBy(id = "signup-btn")
   private WebElement singup;

   @FindBy(id = "success-msg")
   private WebElement signUpSuccess;

   //shows up only after signUpSuccess.Display is true
   @FindBy(id = "login-link")
   private WebElement loginLink;

   @FindBy(id = "error-msg")
   private WebElement signUpFailed;

   @FindBy(className = "display-5")
   private WebElement title;

   private JavascriptExecutor jE;

   private WebDriverWait wait;

   public SignupPageObject(WebDriver d){
      wait = new WebDriverWait(d, 4, 5000);
      jE = (JavascriptExecutor) d;

      PageFactory.initElements(d, this);
   }

   public void signup(String firstname, String lastname,
                      String username, String password){
      /*firstNameField.sendKeys(firstname);
      lastNameField.sendKeys(lastname);
      usernameField.sendKeys(username);
      passwordField.sendKeys(password);
      submit.click();
       */
      jE.executeScript("arguments[0].value='" + firstname + "';", firstNameField);
      jE.executeScript("arguments[0].value='" + lastname + "';", lastNameField);
      jE.executeScript("arguments[0].value='" + username + "';", usernameField);
      jE.executeScript("arguments[0].value='" + password + "';", passwordField);
      jE.executeScript("arguments[0].click();", singup);
   }

   public void backToLoginAfterSignupSuccess(){
      /*if (signUpSuccess.isDisplayed()){
         loginLink.submit();
      }
       */
      wait.until(ExpectedConditions.elementToBeClickable(signUpSuccess));
      if (signUpSuccess.isDisplayed()){
         jE.executeScript("arguments[0].click();", loginLink);
      }
   }

   public void backToLogin(){
      loginLink.submit();
   }

   public boolean SigningupFailed(){
      return signUpFailed.isDisplayed();
   }

   public boolean signingupSuccess(){
      return signUpSuccess.isDisplayed();
   }

   public boolean atSignupPage(){
      return title.getText().equals("Sign Up");
   }
}
