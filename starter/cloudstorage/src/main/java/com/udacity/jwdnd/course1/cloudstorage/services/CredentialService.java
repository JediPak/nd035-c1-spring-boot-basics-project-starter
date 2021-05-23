package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

   private final CredentialMapper credentialMapper;
   private final EncryptionService encryptionService;

   public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
      this.credentialMapper = credentialMapper;
      this.encryptionService = encryptionService;
   }

   public List <Credential> getCredential(){
      return credentialMapper.getCredential();
   }

   /*private String getEncodedKey(){
      SecureRandom random = new SecureRandom();
      byte[] key = new byte[16];
      random.nextBytes(key);
      String encodedKey = Base64.getEncoder().encodeToString(key);
      return encodedKey;
   }*/
   public String getEncodedKey()
   {
      try
      {
         SecureRandom random = new SecureRandom();
         byte[] key = new byte[16];
         random.nextBytes(key);
         return Base64.getEncoder().encodeToString(key);
      }
      catch (Exception e)
      { e.printStackTrace();
      }
      return null;
   }

   public boolean usernameAvailable(String username, Integer userid){
      List<Credential> credentialList = credentialMapper.usernameAvailable(username, userid);
      System.out.println("credentialList: "+credentialList);
      System.out.println("credentialList (all): "+credentialMapper.getCredential());
      return credentialList.isEmpty();
   }

   public String getDecryptedPassword(Credential credential){
      String encodedKey = credential.getKey();
      String encryptedPassword = credential.getPassword();
      return encryptionService.decryptValue(encryptedPassword, encodedKey);

   }

   //returns credentialid of newly created credential
   public int createCredential(Credential credential){
      String encodedKey = encryptionService.getEncodedKey();

      System.out.println(" create password: "+credential.getPassword());
      System.out.println("create encodedKey: "+ encodedKey);
      credential.setKey(encodedKey);

      //not working
      String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
      credential.setPassword(encryptedPassword);

      //temporary solution
      //credential.setPassword(credential.getPassword());

      return credentialMapper.createCredential(credential);

   }

   //returns number of updated rows
   public int updateCredential(Credential credential){
      String encodedKey = encryptionService.getEncodedKey();

      System.out.println("update url: "+credential.getUrl());
      System.out.println("update username: "+credential.getUsername());
      System.out.println("update password: "+credential.getPassword());

      credential.setUrl(credential.getUrl());
      credential.setUsername(credential.getUsername());

      String key = getCredentialsById(credential.getCredentialid()).getKey();
      //not working
      String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
      credential.setPassword(encryptedPassword);

      //temporary solution
      //credential.setPassword(credential.getPassword());

      return credentialMapper.updateCredential(credential);

   }

   //returns number of deleted rows
   public int deleteCredential(Integer credentialid){
      return credentialMapper.deleteCredential(credentialid);
   }

   //returns list of all credentials of one user
   public List <Credential> getCredentialsByUserId(Integer userid){
      List <Credential> credentialList = credentialMapper.getCredentialByUserId(userid);
      if (credentialList == null){
         return new ArrayList <Credential>();
      }
      return credentialList;
   }

   public Credential getCredentialsById(Integer credentialid){
      return credentialMapper.getCredentialById(credentialid);
   }

}
