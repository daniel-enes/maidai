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

    @NotBlank
    @Column(name = "nome", unique = true)
    private String name;

    @OneToMany(targetEntity = Advisor.class, mappedBy ="ppg")
    private List<Advisor> advisorList = new ArrayList<>();

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

    public List<Advisor> getAdvisorList() {
        return advisorList;
    }

    public void setAdvisorList(List<Advisor> advisorList) {
        this.advisorList = advisorList;
    }

    @Override
    public String toString() {
        return "PPG{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
