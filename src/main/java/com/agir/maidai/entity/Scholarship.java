package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "bolsas")
public class Scholarship extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message="A data de início precisa ser definida.")
    @Column(name = "inicio")
    private LocalDate start;

    @NotNull(message="A data do fim precisa ser definida.")
    @Column(name = "fim")
    private LocalDate end;

    @NotNull(message="O tipo de bolsa precisa ser definido.")
    @OneToOne
    @JoinColumn(name = "tipos_bolsa_id", referencedColumnName = "id", nullable = false)
    private ScholarshipType scholarshipType;

    @NotNull(message="O Projeto que a bolsa pertence precisa ser definido.")
    @ManyToOne
    @JoinColumn(name = "projetos_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @NotNull(message="Um bolsista precisa ser definido para a bolsa.")
    @OneToOne
    @JoinColumn(name = "pessoas_id", referencedColumnName = "id", nullable = false)
    private Person person;

    public Scholarship() {
    }

    public Scholarship(Integer id, @NotNull LocalDate start, @NotNull LocalDate end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ScholarshipType getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(ScholarshipType scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Scholarship{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
