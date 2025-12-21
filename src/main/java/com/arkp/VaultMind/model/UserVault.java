package com.arkp.VaultMind.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(
        name = "user_vault",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name","user_id"})
        }
)
public class UserVault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String name;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDate.now();
        this.updatedAt=LocalDate.now();

    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDate.now();
    }


}
