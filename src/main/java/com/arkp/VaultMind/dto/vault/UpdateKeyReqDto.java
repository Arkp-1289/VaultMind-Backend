package com.arkp.VaultMind.dto.vault;

public class UpdateKeyReqDto {
    private String oldKey;
    private String newKey;

    public String getOldKey() {
        return oldKey;
    }

    public void setOldKey(String oldKey) {
        this.oldKey = oldKey;
    }

    public String getNewKey() {
        return newKey;
    }

    public void setNewKey(String newKey) {
        this.newKey = newKey;
    }
}
