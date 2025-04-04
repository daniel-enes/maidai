package com.agir.maidai.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AdvisorProjectId implements Serializable {

    private Integer advisorId;
    private Integer projectId;
}
