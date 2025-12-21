package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.dto.*;
import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.service.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> resetMasterKey(@AuthenticationPrincipal User loggedUser,@RequestBody VaultKeyReqDto vaultKeyReqDto){
        try {
            return vaultService.resetKey(loggedUser,vaultKeyReqDto.getMasterKey().trim());
        } catch (Exception e) {
            return  new ResponseEntity<>("Something went wrong in reset",HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/data")
    public ResponseEntity<String> setVault(@AuthenticationPrincipal User loggedUser, @RequestBody VaultReqDto vaultReqDto) throws Exception {
        System.out.println("hash: "+vaultReqDto.getMasterKey());
        return vaultService.setVault(loggedUser,vaultReqDto,vaultReqDto.getMasterKey().trim());
    }


    @PutMapping("/data")
    public ResponseEntity<?> updateVault(@AuthenticationPrincipal User loggedUser, @RequestBody UpdateVaultReqDto updateVaultDto){
        try {
            return  vaultService.setVault1(loggedUser,updateVaultDto);
        } catch (Exception e) {
            return new ResponseEntity<>("Id not found or not owned by user",HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/data")
    public ResponseEntity<List<VaultResDto>> getVault(@AuthenticationPrincipal User loggedUser){
        return vaultService.getVaults(loggedUser);
    }


    @PostMapping("/data/{id}")
    public ResponseEntity<String> getVaultById(@AuthenticationPrincipal User loggedUser, @PathVariable int id, @RequestBody VaultKeyReqDto vaultByIdReqDto) throws Exception {
        try {
            return vaultService.getVaultById(loggedUser,id,vaultByIdReqDto.getMasterKey());
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong Id or not owned by user", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVault(@AuthenticationPrincipal User loggedUser,@RequestBody VaultDeleteReqDto deleteReqDto){
        try {
            return vaultService.deleteVault(loggedUser,deleteReqDto.getId());
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong Id or not owned by user", HttpStatus.UNAUTHORIZED);        }
    }

}
