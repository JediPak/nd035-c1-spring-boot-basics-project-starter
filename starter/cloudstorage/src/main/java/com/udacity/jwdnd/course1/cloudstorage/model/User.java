package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class User {

   private Integer userid;
   private String username;
   private String salt; //check if this should be an array
   private String password;
   private String firstname;
   private String lastname;

   public User(Integer userid, String username, String salt, String password, String firstname, String lastname) {
      this.userid = userid;
      this.username = username;
      this.salt = salt;
      this.password = password;
      this.firstname = firstname;
      this.lastname = lastname;
   }

   public Integer getUserid() {
      return userid;
   }

   public void setUserid(Integer userid) {
      this.userid = userid;
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
      return userid.equals(user.userid) && Objects.equals(username, user.username) && Objects.equals(salt, user.salt) && Objects.equals(password, user.password) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname);
   }

   @Override
   public int hashCode() {
      return Objects.hash(userid, username, salt, password, firstname, lastname);
   }

   @Override
   public String toString() {
      return "User{" +
              "userid=" + userid +
              ", username='" + username + '\'' +
              ", salt='" + salt + '\'' +
              ", password='" + password + '\'' +
              ", firstname='" + firstname + '\'' +
              ", lastname='" + lastname + '\'' +
              '}';
   }
}
