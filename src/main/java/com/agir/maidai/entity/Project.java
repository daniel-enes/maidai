package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="projetos")
public class Project extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "nome", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "empresas_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "project")
    private List<AdvisorProject> advisorProjects = new ArrayList<>();

    public Project() {
    }

    public Project(Integer id, String name) {
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
        this.name = name.trim();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<AdvisorProject> getAdvisorProjects() {
        return advisorProjects;
    }

    public void setAdvisorProjects(List<AdvisorProject> advisorProjects) {
        this.advisorProjects = advisorProjects;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
