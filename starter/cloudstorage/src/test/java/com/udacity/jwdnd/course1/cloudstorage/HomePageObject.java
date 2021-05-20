package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePageObject {

   @FindBy(id = "logout-btn")
   private WebElement logout;


   /*
   * Notes
   */
   @FindBy(id="nav-notes-tab")
   private WebElement noteTab;

   @FindBy(id = "create-note")
   private WebElement noteCreate;

   @FindBy(id = "note-saveChanges")
   private WebElement noteSave;

   /*note fields*/
   @FindBy(id = "note-title")
   private WebElement noteTitle;

   @FindBy(id = "note-description")
   private WebElement noteDescription;

   /*note field views*/
   @FindBy(className="note-title-view")
   private List <WebElement> noteTitleView;

   @FindBy(className="note-description-view")
   private List <WebElement> noteDescriptionView;

   @FindBy(className = "noteEdit-btn")
   private List<WebElement> noteEdit;

   @FindBy(className = "noteDelete-btn")
   private List<WebElement> noteDelete;

   /*
   * Credentials
   */
   @FindBy(id="nav-credentials-tab")
   private WebElement credentialTab;

   @FindBy(id = "create-credential")
   private WebElement credentialCreate;

   /*Credential fields*/
   @FindBy(id = "credential-url")
   private WebElement credentialUrl;

   @FindBy(id = "credential-username")
   private WebElement credentialUsername;

   @FindBy(id = "credential-password")
   private WebElement credentialPassword;

   @FindBy(id = "credential-saveChanges")
   private WebElement credentialSave;

   /*Credential field views*/
   @FindBy(className="credential-url-view")
   private List <WebElement> credentialUrlView;

   @FindBy(className="credential-username-view")
   private List <WebElement> credentialUsernameView;

   @FindBy(className="credential-password-view")
   private List <WebElement> credentialPasswordView;

   @FindBy(className = "credentialEdit-btn")
   private List<WebElement> credentialEdit;

   @FindBy(className = "credentialDelete-btn")
   private List<WebElement> credentialDelete;


   private JavascriptExecutor jE;

   public HomePageObject(WebDriver d){
      jE = (JavascriptExecutor) d;
      PageFactory.initElements(d, this);
   }

   public void logout(){
      jE.executeScript("arguments[0].click();", logout);
   }

   public void createNote(String title, String description){

      jE.executeScript("arguments[0].click();", noteTab);
      jE.executeScript("arguments[0].click();", noteCreate);

      jE.executeScript("arguments[0].value='" + title + "';", noteTitle);
      jE.executeScript("arguments[0].value='" + description + "';", noteDescription);
      jE.executeScript("arguments[0].click();", noteSave);
   }


   public void editNote(Integer index, String title, String description){


      jE.executeScript("arguments[0].click();", noteTab);
      jE.executeScript("arguments[0].click();", noteEdit.get(index));

      jE.executeScript("arguments[0].value='" + title + "';", noteTitle);
      jE.executeScript("arguments[0].value='" + description + "';", noteDescription);
      jE.executeScript("arguments[0].click();", noteSave);
   }


   public void deleteNote(Integer index){
      jE.executeScript("arguments[0].click();", noteDelete.get(index));
   }

   public List<List<String>> viewNote(){
      List<List<String>> viewNotes = new ArrayList <>();
      String title, description;

      System.out.println("noteTitleView.size(): "+noteTitleView.size());
      //System.out.println("noteTitleView.get(0).getText(): "+noteTitleView.get(0).getText());

      //length of noteTitleView and noteDescriptionView should be equal
      for(int i = 0 ; i < noteTitleView.size() ; i ++){
         List<String> viewNote = new ArrayList <>();
         title = noteTitleView.get(i).getText();
         description = noteDescriptionView.get(i).getText();
         viewNote.add(title);
         System.out.println("viewNote (title): "+viewNote.toString());
         viewNote.add(description);
         System.out.println("viewNote (description): "+viewNote.toString());
         viewNotes.add(viewNote);
         System.out.println("viewNotes: "+viewNotes.toString());
      }
      return viewNotes;
   }

   public void createCredential(String url, String username, String password){

      jE.executeScript("arguments[0].click();", credentialTab);
      jE.executeScript("arguments[0].click();", credentialCreate);

      jE.executeScript("arguments[0].value='" + url + "';", credentialUrl);
      jE.executeScript("arguments[0].value='" + username + "';", credentialUsername);
      jE.executeScript("arguments[0].value='" + password + "';", credentialPassword);
      jE.executeScript("arguments[0].click();", credentialSave);
   }

   public void editCredential(Integer index, String url, String username, String password){

      jE.executeScript("arguments[0].click();", credentialTab);
      jE.executeScript("arguments[0].click();", credentialEdit.get(index));

      jE.executeScript("arguments[0].value='" + url + "';", credentialUrl);
      jE.executeScript("arguments[0].value='" + username + "';", credentialUsername);
      jE.executeScript("arguments[0].value='" + password + "';", credentialPassword);
      jE.executeScript("arguments[0].click();", credentialSave);
   }

   public List<List<String>> viewCredential(){
      List<List<String>> viewCredentials = new ArrayList <>();

      //length of credentialUrlView, credentialUsernameView, and credentialPasswordView should be equal
      System.out.println("credentialUrlView.size(): "+credentialUrlView.size());
      for(int i = 0 ; i < credentialUrlView.size() ; i ++){
         List<String> viewCredential = new ArrayList <>();
         viewCredential.add(credentialUrlView.get(i).getText());
         viewCredential.add(credentialUsernameView.get(i).getText());
         viewCredential.add(credentialPasswordView.get(i).getText());
         viewCredentials.add(viewCredential);
      }
      return viewCredentials;
   }

   public void deleteCredential(Integer index){
      jE.executeScript("arguments[0].click();", credentialDelete.get(index));
   }
}
