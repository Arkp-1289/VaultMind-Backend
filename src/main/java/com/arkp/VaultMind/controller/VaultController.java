package com.arkp.VaultMind.controller;

import com.arkp.VaultMind.model.User;
import com.arkp.VaultMind.service.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/vault")
public class VaultController {

    @Autowired
    VaultService vaultService;

    @PostMapping("/key")
    public ResponseEntity<String> setMasterKey(@AuthenticationPrincipal User loggedUser, @RequestBody String masterKey){
       return  vaultService.setkey(loggedUser,masterKey);

    }

}
