package com.arkp.VaultMind.dto;

public class VaultReqDto {
    private String name;
    private String password;

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

    public VaultReqDto(String password, String name) {
        this.password = password;
        this.name = name;
    }
}
