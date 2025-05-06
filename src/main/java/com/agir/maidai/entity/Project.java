package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="projetos")
public class Project extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //@NotBlank
    @Column(name = "nome")
    private String name;

    //@NotNull
    @Column(name = "inicio")
    private LocalDate start;

    //@NotNull
    @Column(name = "fim")
    private LocalDate end;

    @ManyToOne
    @JoinColumn(name = "empresas_id", referencedColumnName = "id", nullable = false)
    //@JoinColumn(name = "empresas_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(targetEntity = Scholarship.class, mappedBy = "project")
    private List<Scholarship> scholarshipList = new ArrayList<>();

    @Transient
    private Integer advisorId;

    @Transient
    private Integer coAdvisorId;

    @PostLoad
    private void populateTransientFields() {
        if (advisor != null) {
            this.advisorId = advisor.getId();
        }
        if (coAdvisor != null) {
            this.coAdvisorId = coAdvisor.getId();
        }
    }

    @ManyToOne
    //@JoinColumn(name = "orientador", referencedColumnName = "pessoas_id")
    @JoinColumn(name = "orientador", referencedColumnName = "pessoas_id", nullable = false)
    private Advisor advisor;


    @ManyToOne
    @JoinColumn(name = "coorientador", referencedColumnName = "pessoas_id", nullable = true)
    private Advisor coAdvisor;

    public Project() {
    }

    public Project(Integer id, String name, LocalDate start, LocalDate end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
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
        this.name = name.trim();
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Scholarship> getScholarshipList() {
        return scholarshipList;
    }

    public void setScholarshipList(List<Scholarship> scholarshipList) {
        this.scholarshipList = scholarshipList;
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
