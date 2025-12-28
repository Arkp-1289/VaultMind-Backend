package com.arkp.VaultMind.dto.profile;

public class ProfileResponseDto {

    private Long profileId;
    private String name;
    private String phone;
    private String email;

    public ProfileResponseDto(Long profileId, String name, String phone, String email) {
        this.profileId = profileId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }
}
