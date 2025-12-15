package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.dto.UpdateKeyReqDto;
import com.arkp.VaultMind.dto.VaultReqDto;
import com.arkp.VaultMind.dto.VaultResDto;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.model.UserVault;
import com.arkp.VaultMind.service.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/vault")
public class VaultController {

    @Autowired
    VaultService vaultService;



    @PostMapping("/key")
    public ResponseEntity<String> UpdateMasterKey(@AuthenticationPrincipal User loggedUser, @RequestBody UpdateKeyReqDto updateKeyReqDto){
        try {
            return  vaultService.setkey(loggedUser,updateKeyReqDto.getOldKey().trim(), updateKeyReqDto.getNewKey().trim());
        } catch (Exception e) {
            return  new ResponseEntity<>("invalid user",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/reset/key")
    public ResponseEntity<String> resetMasterKey(@AuthenticationPrincipal User loggedUser,@RequestBody String masterKey){
        try {
            return vaultService.resetKey(loggedUser,masterKey.trim());
        } catch (Exception e) {
            return  new ResponseEntity<>("Something went wrong in reset",HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/data")
    public ResponseEntity<String> setVault(@AuthenticationPrincipal User loggedUser, @RequestBody VaultReqDto vaultReqDto) throws Exception {
        return vaultService.setVault(loggedUser,vaultReqDto,vaultReqDto.getMasterKey().trim());
    }

    @GetMapping("/data")
    public ResponseEntity<List<VaultResDto>> getVault(@AuthenticationPrincipal User loggedUser){
        return vaultService.getVaults(loggedUser);
    }

    @PostMapping("/data/{id}")
    public ResponseEntity<String> getVaultById(@AuthenticationPrincipal User loggedUser,@PathVariable int id, @RequestBody String masterKey) throws Exception {
        try {
            return vaultService.getVaultById(loggedUser,id,masterKey);
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong Id or not owned by user", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/delete")
   public ResponseEntity<String> deleteVault(@AuthenticationPrincipal User loggedUser,@RequestBody int id){
        try {
            return vaultService.deleteVault(loggedUser,id);
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong Id or not owned by user", HttpStatus.UNAUTHORIZED);        }
    }

}
