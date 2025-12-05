package com.arkp.VaultMind.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(name = "notes")
public class UserNotes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;


    private LocalDate createdAt;


    private  LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id" ,referencedColumnName = "userId")
   private User user;

    @PrePersist
    public void prepersit(){
        this.createdAt=LocalDate.now();
        this.updatedAt=LocalDate.now();
    }


    @PreUpdate
    public void preupdate(){
        this.updatedAt=LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
