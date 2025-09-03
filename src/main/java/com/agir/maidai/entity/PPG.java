package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ppg")
public class PPG extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Nome não pode ficar em branco ")
    @Column(name = "nome", unique = true)
    private String name;

    @ManyToMany(mappedBy = "ppgList")
    private List<Person> personList = new ArrayList<>();

    public PPG() {
    }

    public PPG(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "PPG{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
