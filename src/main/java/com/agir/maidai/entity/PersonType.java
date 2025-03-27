package com.agir.maidai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_pessoas")
public class PersonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tipo")
    private String type;

    public PersonType() {
    }

    public PersonType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PersonType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
