package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class User {

   private Integer userId;
   private String username;
   private String salt; //check if this should be an array
   private String password;
   private String firstname;
   private String lastname;

   public User(Integer userId, String username, String salt, String password, String firstname, String lastname) {
      this.userId = userId;
      this.username = username;
      this.salt = salt;
      this.password = password;
      this.firstname = firstname;
      this.lastname = lastname;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getSalt() {
      return salt;
   }

   public void setSalt(String salt) {
      this.salt = salt;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof User)) return false;
      User user = (User) o;
      return userId.equals(user.userId) && Objects.equals(username, user.username) && Objects.equals(salt, user.salt) && Objects.equals(password, user.password) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname);
   }

   @Override
   public int hashCode() {
      return Objects.hash(userId, username, salt, password, firstname, lastname);
   }

   @Override
   public String toString() {
      return "User{" +
              "userId=" + userId +
              ", username='" + username + '\'' +
              ", salt='" + salt + '\'' +
              ", password='" + password + '\'' +
              ", firstname='" + firstname + '\'' +
              ", lastname='" + lastname + '\'' +
              '}';
   }
}
