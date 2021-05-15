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

   public String getDecryptedPassword(Credential credential){
      String encodedKey = credential.getKey();
      String encryptedPassword = credential.getPassword();
      return encryptionService.decryptValue(encryptedPassword, encodedKey);

   }

   public int createCredential(Credential credential){
      String encodedKey = encryptionService.getEncodedKey();

      System.out.println(" create password: "+credential.getPassword());
      System.out.println("create encodedKey: "+ encodedKey);
      credential.setKey(encodedKey);
      String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
      credential.setPassword(encryptedPassword);
      //credential.setPassword(credential.getPassword());

      return credentialMapper.createCredential(credential);

   }

   public int updateCredential(Credential credential){
      String encodedKey = encryptionService.getEncodedKey();

      System.out.println("update password: "+credential.getPassword());

      credential.setUrl(credential.getUrl());
      credential.setUrl(credential.getUsername());
      //String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
      //credential.setPassword(encryptedPassword);
      credential.setPassword(credential.getPassword());

      return credentialMapper.updateCredential(credential);

   }

   public List <Credential> getCredentialsByUserId(Integer userid){
      List <Credential> credentialList = credentialMapper.getCredentialByUserId(userid);
      if (credentialList == null){
         return new ArrayList <Credential>();
      }
      return credentialList;
   }

}
