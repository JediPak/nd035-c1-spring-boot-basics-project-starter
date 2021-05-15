package com.udacity.jwdnd.course1.cloudstorage.model;

import java.io.InputStream;
import java.util.Objects;

public class File {

   private Integer fileId;
   private String filename;
   private String contenttype;
   private String filesize;
   private Integer userid;
   private InputStream filedata;

   public File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, InputStream filedata) {
      this.fileId = fileId;
      this.filename = filename;
      this.contenttype = contenttype;
      this.filesize = filesize;
      this.userid = userid;
      this.filedata = filedata;
   }

   public Integer getFileId() {
      return fileId;
   }

   public void setFileId(Integer fileId) {
      this.fileId = fileId;
   }

   public String getFilename() {
      return filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public String getContenttype() {
      return contenttype;
   }

   public void setContenttype(String contenttype) {
      this.contenttype = contenttype;
   }

   public String getFilesize() {
      return filesize;
   }

   public void setFilesize(String filesize) {
      this.filesize = filesize;
   }

   public Integer getUserid() {
      return userid;
   }

   public void setUserid(Integer userid) {
      this.userid = userid;
   }

   public InputStream getFiledata() {
      return filedata;
   }

   public void setFiledata(InputStream filedata) {
      this.filedata = filedata;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof File)) return false;
      File file = (File) o;
      return fileId.equals(file.fileId) && Objects.equals(filename, file.filename) && Objects.equals(contenttype, file.contenttype) && Objects.equals(filesize, file.filesize) && Objects.equals(userid, file.userid) && Objects.equals(filedata, file.filedata);
   }

   @Override
   public int hashCode() {
      return Objects.hash(fileId, filename, contenttype, filesize, userid, filedata);
   }

   @Override
   public String toString() {
      return "File{" +
              "fileId=" + fileId +
              ", filename='" + filename + '\'' +
              ", contenttype='" + contenttype + '\'' +
              ", filesize='" + filesize + '\'' +
              ", userid=" + userid +
              ", filedata=" + filedata +
              '}';
   }
}
