package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialService {

   private final CredentialMapper credentialMapper;
   private final EncryptionService encryptionService;

   public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
      this.credentialMapper = credentialMapper;
      this.encryptionService = encryptionService;
   }

   private String getEncodedKey(){
      SecureRandom random = new SecureRandom();
      byte[] key = new byte[16];
      random.nextBytes(key);
      String encodedKey = Base64.getEncoder().encodeToString(key);
      return encodedKey;
   }

   public String getDecryptedPassword(Credential credential){
      String encodedKey = credential.getKey();
      String encryptedPassword = credential.getPassword();
      return encryptionService.decryptValue(encryptedPassword, encodedKey);

   }

   public int createCredential(Credential credential){
      String encodedKey = getEncodedKey();
      String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

      credential.setKey(encodedKey);
      credential.setPassword(encryptedPassword);

      return credentialMapper.createCredential(credential);

   }

}
