package com.arkp.VaultMind.service;

import com.arkp.VaultMind.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class VaultService {

    private boolean verifyKey(String masterKey, String keyHash) {
       return BCrypt.checkpw(masterKey,keyHash);
    }

    private String hashKey(String masterKey) {
        return BCrypt.hashpw(masterKey,BCrypt.gensalt());
    }

    public ResponseEntity<String> setkey(User loggedUser, String masterKey) {
        if (verifyKey(masterKey,loggedUser.getKeyHash())){
            return new ResponseEntity<>("Changes saved", HttpStatus.OK);
        }
         String hashedKey = hashKey(masterKey);
         return new ResponseEntity<>(hashedKey,HttpStatus.OK);


    }


}
