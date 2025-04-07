package com.agir.maidai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AdvisorProjectId implements Serializable {

    @Column(name = "orientadores_pessoas_id")
    private Integer advisorId;

    @Column(name = "projetos_id")
    private Integer projectId;

    public AdvisorProjectId() {

    }

    public AdvisorProjectId(Integer advisorId, Integer projectId) {
        this.advisorId = advisorId;
        this.projectId = projectId;
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
