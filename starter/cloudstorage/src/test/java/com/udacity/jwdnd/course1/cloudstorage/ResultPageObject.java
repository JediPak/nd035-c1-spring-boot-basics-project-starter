package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPageObject {

   @FindBy(className = "continue-home")
   private WebElement continueToHome;

   @FindBy(className = "result-msg")
   private WebElement resultMsg;

   private JavascriptExecutor jE;

   public ResultPageObject(WebDriver d){
      jE = (JavascriptExecutor) d;
      PageFactory.initElements(d, this);
   }

   public String getResultMsg(){
      return resultMsg.getText();
   }

   public void continueToHome(){

      jE.executeScript("arguments[0].click();", continueToHome);
   }


}
