package com.agir.maidai.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orientadores")
public class Advisor {

    @Id
    @Column(name = "pessoas_id")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pessoas_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "ppg_id", nullable = false)
    private PPG ppg;

    @OneToMany(mappedBy = "advisor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AdvisorProject> advisorProjects = new ArrayList<>();

    @OneToMany(mappedBy = "coAdvisor")
    private List<AdvisorProject> coAdvisorProjects = new ArrayList<>();

    public Advisor() {
    }

    public Advisor(Person person) {
        this.id = person != null ? person.getId() : null;
        this.person = person;
        this.ppg = null;
    }

    public Advisor(Person person, PPG ppg) {
        this.id = person != null ? person.getId() : null;
        this.person = person;
        this.ppg = ppg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PPG getPpg() {
        return ppg;
    }

    public void setPpg(PPG ppg) {
        this.ppg = ppg;
    }

    public List<AdvisorProject> getAdvisorProjects() {
        return advisorProjects;
    }

    public void setAdvisorProjects(List<AdvisorProject> advisorProjects) {
        this.advisorProjects = advisorProjects;
    }

    public List<AdvisorProject> getCoAdvisorProjects() {
        return coAdvisorProjects;
    }

    public void setCoAdvisorProjects(List<AdvisorProject> coAdvisorProjects) {
        this.coAdvisorProjects = coAdvisorProjects;
    }

    @Override
    public String toString() {
        return "Advisor{" +
                "id=" + id +
                '}';
    }
}
