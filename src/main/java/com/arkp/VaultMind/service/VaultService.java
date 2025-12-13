package com.arkp.VaultMind.service;

import com.arkp.VaultMind.dto.VaultReqDto;
import com.arkp.VaultMind.dto.VaultResDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserVault;
import com.arkp.VaultMind.repo.AuthRepo;
import com.arkp.VaultMind.repo.VaultRepo;
import com.arkp.VaultMind.util.VaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaultService {


    @Autowired
    AuthRepo authRepo;

    @Autowired
    VaultRepo vaultRepo;

    private boolean verifyKey(String masterKey, String keyHash) {
        if (keyHash == null) {
            System.out.println("key hash is null");
            return true;
        }
        return BCrypt.checkpw(masterKey, keyHash);
    }

    private String hashKey(String masterKey) {
        return BCrypt.hashpw(masterKey, BCrypt.gensalt());
    }

    public ResponseEntity<String> setkey(User loggedUser, String masterKey,String newKey) {
        if (!verifyKey(masterKey, loggedUser.getHashKey())) {
            return new ResponseEntity<>("Key is incorrect!", HttpStatus.OK);
        }
        try {

            String hashedKey = hashKey(newKey);
            String newSalt =VaultUtil.generateSalt();
            List<UserVault> VaultList = new ArrayList<>();
            VaultList=vaultRepo.findAllByUser(loggedUser).orElseThrow();
            System.out.println("old vaults: "+VaultList);
            for (UserVault dto:VaultList){
                String decrypt_pwd=VaultUtil.decrypt(dto.getPassword(),masterKey,loggedUser.getSalt());
                String encrypt_pwd=VaultUtil.encrypt(decrypt_pwd,newKey,newSalt);
                dto.setPassword(encrypt_pwd);
            }
            System.out.println("new vaults: "+VaultList);
            loggedUser.setSalt(newSalt);
            loggedUser.setHashKey(hashedKey);
            vaultRepo.saveAll(VaultList);
            authRepo.save(loggedUser);
            return new ResponseEntity<>(hashedKey, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_ACCEPTABLE);
        }
    }


    public ResponseEntity<String> setVault(User loggedUser, VaultReqDto vaultReqDto,String masterKey) throws Exception {

        if (!verifyKey(vaultReqDto.getMasterKey(),loggedUser.getHashKey())){
            System.out.println(masterKey+" "+vaultReqDto.getMasterKey());
            return new ResponseEntity<>("Incorrect key--"+loggedUser.getHashKey()+" "+masterKey,HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            String encryptedPassword = VaultUtil.encrypt(vaultReqDto.getPassword(), masterKey, loggedUser.getSalt());
            UserVault vault = new UserVault();
            vault.setName(vaultReqDto.getName());
            vault.setPassword(encryptedPassword);
            vault.setUser(loggedUser);
            vaultRepo.save(vault);

            return new ResponseEntity<>("Vault Created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<List<VaultResDto>> getVaults(User loggedUser) {
            List<VaultResDto> vaultList = vaultRepo.findByUser(loggedUser);
            return new ResponseEntity<>(vaultList, HttpStatus.OK);
    }

      public ResponseEntity<String> getVaultById(User loggedUser, int id, String masterKey) throws Exception {
        if (!verifyKey(masterKey,loggedUser.getHashKey())){
            return new ResponseEntity<>("Incorrect key",HttpStatus.UNAUTHORIZED);
        }

        UserVault vault =  vaultRepo.findByIdAndUser(id,loggedUser).orElseThrow();
        String DecryptedPassword=VaultUtil.decrypt(vault.getPassword(),masterKey,loggedUser.getSalt());
        System.out.println("dpwd "+ DecryptedPassword);
        return new ResponseEntity<>(DecryptedPassword,HttpStatus.OK);
    }

    public ResponseEntity<String> resetKey(User loggedUser,String masterKey) {


        int rows_effected= vaultRepo.deleteByUser(loggedUser);
        System.out.println("deleted");
        loggedUser.setHashKey(hashKey(masterKey));
        loggedUser.setSalt(VaultUtil.generateSalt());
        authRepo.save(loggedUser);
        return new ResponseEntity<>("key reset successful: "+loggedUser.getHashKey(),HttpStatus.OK);
    }

    public User checkUser(User loggedUser) {
        User freshUser = authRepo.findById(loggedUser.getUserId()).orElseThrow();
        return freshUser;
    }
}
