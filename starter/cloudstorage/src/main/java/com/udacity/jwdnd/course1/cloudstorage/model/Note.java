package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class Note {

   private Integer noteid;
   private String notetitle;
   private String notedescription;
   private Integer userid;

   public Note(Integer noteid, String notetitle, String notedescription, Integer userid) {
      this.noteid = noteid;
      this.notetitle = notetitle;
      this.notedescription = notedescription;
      this.userid = userid;
   }

   public Integer getNoteid() {
      return noteid;
   }

   public void setNoteid(Integer noteid) {
      this.noteid = noteid;
   }

   public String getNotetitle() {
      return notetitle;
   }

   public void setNotetitle(String notetitle) {
      this.notetitle = notetitle;
   }

   public String getNotedescription() {
      return notedescription;
   }

   public void setNotedescription(String notedescription) {
      this.notedescription = notedescription;
   }

   public Integer getUserid() {
      return userid;
   }

   public void setUserid(Integer userid) {
      this.userid = userid;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Note)) return false;
      Note note = (Note) o;
      return noteid.equals(note.noteid) && Objects.equals(notetitle, note.notetitle) && Objects.equals(notedescription, note.notedescription) && userid.equals(note.userid);
   }

   @Override
   public int hashCode() {
      return Objects.hash(noteid, notetitle, notedescription, userid);
   }

   @Override
   public String toString() {
      return "Note{" +
              "noteid=" + noteid +
              ", notetitle='" + notetitle + '\'' +
              ", notedescription='" + notedescription + '\'' +
              ", userid=" + userid +
              '}';
   }
}
