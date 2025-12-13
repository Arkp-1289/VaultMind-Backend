package com.arkp.VaultMind.dto;

public class VaultReqDto {
    private String name;
    private String password;
    private String masterKey;

    public VaultReqDto(String name, String password, String masterKey) {
        this.name = name;
        this.password = password;
        this.masterKey = masterKey;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
