package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name="projetos")
public class Project extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "nome")
    private String name;

    @NotNull
    @Column(name = "inicio")
    private Date start;

    @NotNull
    @Column(name = "fim")
    private Date end;

    @ManyToOne
    @JoinColumn(name = "empresas_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Transient
    private Integer advisorId;

    @Transient
    private Integer coAdvisorId;

    @ManyToOne
    @JoinColumn(name = "orientador", referencedColumnName = "pessoas_id", nullable = false)
    private Advisor advisor;


    @ManyToOne
    @JoinColumn(name = "coorientador", referencedColumnName = "pessoas_id", nullable = true)
    private Advisor coAdvisor;

    public Project() {
    }

    public Project(Integer id, String name, Date start, Date end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    /*public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
    }*/

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
        this.name = name.trim();
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Advisor getCoAdvisor() {
        return coAdvisor;
    }

    public void setCoAdvisor(Advisor coAdvisor) {
        this.coAdvisor = coAdvisor;
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }

    public Integer getCoAdvisorId() {
        return coAdvisorId;
    }

    public void setCoAdvisorId(Integer coAdvisorId) {
        this.coAdvisorId = coAdvisorId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
