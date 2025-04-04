package com.agir.maidai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orientadores_projetos")
public class AdvisorProject {

    @EmbeddedId
    private AdvisorProjectId id;

    @ManyToOne
    @MapsId("advisorId")
    @JoinColumn(name = "orientadores_pessoas_id")
    private Advisor advisor;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "projetos_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "coorientador", nullable = true)
    private Advisor coAdvisor;

    public AdvisorProject() {
    }

    public AdvisorProject(AdvisorProjectId id, Advisor advisor, Project project, Advisor coAdvisor) {
        this.id = id;
        this.advisor = advisor;
        this.project = project;
        this.coAdvisor = coAdvisor;
    }

    public AdvisorProjectId getId() {
        return id;
    }

    public void setId(AdvisorProjectId id) {
        this.id = id;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Advisor getCoAdvisor() {
        return coAdvisor;
    }

    public void setCoAdvisor(Advisor coAdvisor) {
        this.coAdvisor = coAdvisor;
    }
}
