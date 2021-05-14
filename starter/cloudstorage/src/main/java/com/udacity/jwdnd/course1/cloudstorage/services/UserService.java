package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

   private final UserMapper userMapper;
   private final HashService hashService;

   public UserService(UserMapper userMapper, HashService hashService) {
      this.userMapper = userMapper;
      this.hashService = hashService;
   }

   private String getEncodedSalt(){
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[16];
      random.nextBytes(salt);
      String encodedSalt = Base64.getEncoder().encodeToString(salt);
      return encodedSalt;
   }
   public int createUser(User user){
      String encodedSalt = getEncodedSalt();
      String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

      user.setSalt(encodedSalt);
      user.setPassword(hashedPassword);

      return userMapper.createUser(user);
   }

   public User getUserById(Integer userid){
      return userMapper.getUserById(userid);
   }

}
