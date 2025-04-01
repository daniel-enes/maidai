package com.agir.maidai.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_pessoa")
public class PersonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tipo")
    private String type;

    @OneToMany(targetEntity = Person.class, mappedBy ="personType", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Person> personList = new ArrayList<>();

    public PersonType() {
    }

    public PersonType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "PersonType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
