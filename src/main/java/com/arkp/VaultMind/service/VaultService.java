package com.arkp.VaultMind.service;

import com.arkp.VaultMind.dto.vault.UpdateVaultReqDto;
import com.arkp.VaultMind.dto.vault.VaultReqDto;
import com.arkp.VaultMind.dto.vault.VaultResDto;
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
            return new ResponseEntity<>("Key is incorrect!", HttpStatus.UNAUTHORIZED);
        }
        try {
            String hashedKey = hashKey(newKey);
            String newSalt =VaultUtil.generateSalt();
            List<UserVault> VaultList = new ArrayList<>();
            VaultList=vaultRepo.findAllByUser(loggedUser).orElseThrow();
            for (UserVault dto:VaultList){
                String decrypt_pwd=VaultUtil.decrypt(dto.getPassword(),masterKey,loggedUser.getSalt());
                String encrypt_pwd=VaultUtil.encrypt(decrypt_pwd,newKey,newSalt);
                dto.setPassword(encrypt_pwd);
            }
            loggedUser.setSalt(newSalt);
            loggedUser.setHashKey(hashedKey);
            vaultRepo.saveAll(VaultList);
            authRepo.save(loggedUser);
            return new ResponseEntity<>(hashedKey, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<String> setVault(User loggedUser, VaultReqDto vaultReqDto,String masterKey) throws Exception {

        if (!verifyKey(vaultReqDto.getMasterKey(),loggedUser.getHashKey())){
            return new ResponseEntity<>("Incorrect key",HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>("Something went wrong", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<List<VaultResDto>> getVaults(User loggedUser) {
            List<VaultResDto> vaultList = vaultRepo.findByUser(loggedUser);
            return new ResponseEntity<>(vaultList, HttpStatus.OK);
    }

      public ResponseEntity<String> getVaultById(User loggedUser, int id, String masterKey) throws Exception {
        System.out.println("masterkey: "+masterKey);
        if (!verifyKey(masterKey,loggedUser.getHashKey())){
            return new ResponseEntity<>("Incorrect key",HttpStatus.UNAUTHORIZED);
        }

        UserVault vault =  vaultRepo.findByIdAndUser(id,loggedUser).orElseThrow();
        String DecryptedPassword=VaultUtil.decrypt(vault.getPassword(),masterKey,loggedUser.getSalt());
        return new ResponseEntity<>(DecryptedPassword,HttpStatus.OK);
    }

    public ResponseEntity<String> resetKey(User loggedUser,String masterKey) {

        int rows_effected= vaultRepo.deleteByUser(loggedUser);
        loggedUser.setHashKey(hashKey(masterKey));
        loggedUser.setSalt(VaultUtil.generateSalt());
        authRepo.save(loggedUser);
        return new ResponseEntity<>(loggedUser.getHashKey(),HttpStatus.OK);
    }

    public User checkUser(User loggedUser) {
        User freshUser = authRepo.findById(loggedUser.getUserId()).orElseThrow();
        return freshUser;
    }

    public ResponseEntity<String> deleteVault(User loggedUser, int id) {
        UserVault vault = vaultRepo.findByIdAndUser(id,loggedUser).orElseThrow();
        vaultRepo.deleteById(id);
        return new ResponseEntity<>("ID deleted",HttpStatus.OK);
    }

    public ResponseEntity<?> setVault1(User loggedUser, UpdateVaultReqDto updateVaultDto) {
        System.out.println("inside vault1");
        if (!verifyKey(updateVaultDto.getMasterKey(),loggedUser.getHashKey())){
            return new ResponseEntity<>("Incorrect key",HttpStatus.UNAUTHORIZED);
        }
        UserVault vault1= vaultRepo.findByIdAndUser(updateVaultDto.getId(),loggedUser).orElseThrow();

        try {
            String encryptedPassword = VaultUtil.encrypt(updateVaultDto.getPassword(), updateVaultDto.getMasterKey(), loggedUser.getSalt());
            vault1.setPassword(encryptedPassword);
            vaultRepo.save(vault1);

            return new ResponseEntity<>("Vault Created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.UNAUTHORIZED);
        }
    }
}
