package com.agir.maidai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "role")
    private String role;



}
