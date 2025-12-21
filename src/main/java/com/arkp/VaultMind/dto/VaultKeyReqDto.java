package com.arkp.VaultMind.dto;

public class VaultKeyReqDto {
    String masterKey;


    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    @Override
    public String toString() {
        return "VaultByIdReqDto{" +
                "masterKey='" + masterKey + '\'' +
                '}';
    }
}
