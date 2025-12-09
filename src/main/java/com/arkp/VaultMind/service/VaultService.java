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
import java.util.List;
import java.util.Optional;

@Service
public class VaultService {


    @Autowired
    AuthRepo authRepo;

    @Autowired
    VaultRepo vaultRepo;

    private boolean verifyKey(String masterKey, String keyHash) {
        if (keyHash == null) {
            return false;
        }
        return BCrypt.checkpw(masterKey, keyHash);
    }

    private String hashKey(String masterKey) {
        return BCrypt.hashpw(masterKey, BCrypt.gensalt());
    }

    public ResponseEntity<String> setkey(User loggedUser, String masterKey) {
        if (verifyKey(masterKey, loggedUser.getHashKey())) {
            return new ResponseEntity<>("No changes saved", HttpStatus.OK);
        }
        try {
            String hashedKey = hashKey(masterKey);
            String oldSalt=loggedUser.getSalt();
            String oldHashKey=loggedUser.getHashKey();
            loggedUser.setSalt(VaultUtil.generateSalt());
            loggedUser.setHashKey(hashedKey);
            List<UserVault> userVaultList = vaultRepo.findAllByUser(loggedUser).orElseThrow();
            authRepo.save(loggedUser);
            for (UserVault vault:userVaultList){
                String decrypyKey=VaultUtil.decrypt(vault.getPassword(),oldHashKey,oldSalt);
                String encrypted= VaultUtil.encrypt(decrypyKey,loggedUser.getHashKey(),loggedUser.getSalt());
                vault.setPassword(encrypted);
            }
            vaultRepo.saveAll(userVaultList);
            return new ResponseEntity<>(hashedKey, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_ACCEPTABLE);
        }
    }


    public ResponseEntity<String> setVault(User loggedUser, VaultReqDto vaultReqDto) throws Exception {
        try {
            String encryptedPassword = VaultUtil.encrypt(vaultReqDto.getPassword(), loggedUser.getHashKey(), loggedUser.getSalt());
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
        String DecryptedPassword=VaultUtil.decrypt(vault.getPassword(),loggedUser.getHashKey(),loggedUser.getSalt());
        System.out.println("dpwd "+ DecryptedPassword);
        return new ResponseEntity<>(DecryptedPassword,HttpStatus.OK);
    }
}
