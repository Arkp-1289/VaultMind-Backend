package com.arkp.VaultMind.dto.profile;

import jakarta.persistence.Column;

public class UpdateProfileReqDto {
    private Long profileId;
    private String name;
    private String phone;
    private String email;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emial) {
        this.email = emial;
    }
}
