package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

    @NotBlank(message="O nome do projeto não pode ficar em branco.")
    @Column(name = "nome")
    private String name;

    //@NotNull(message="A data de início precisa ser definida.")
    @Column(name = "inicio")
    private LocalDate start;

    //@NotNull(message="A data do fim precisa ser definida.")
    @Column(name = "fim")
    private LocalDate end;

    @Pattern(regexp = "^(19|20)\\d{2}$", message = "O ano deve estar no formato YYYY (entre 1900-2099)")
    @Column(name = "ano_edital", nullable = true)
    private String yearNotice;

    // 1:N relationship for advisor
    @NotNull(message = "O orientador precisa ser definido.")
    @ManyToOne
    @JoinColumn(name = "pessoas_id",
            referencedColumnName = "id",
            nullable = false)
    private Person advisor;

    // N:M relationship for co-advisors
    @ManyToMany
    @JoinTable(
            name = "coorientadores",
            joinColumns = @JoinColumn(name = "projetos_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoas_id")
    )
    private List<Person> coAdvisors = new ArrayList<>();

    @NotNull(message="A empresa precisa ser definida.")
    @ManyToOne
    @JoinColumn(name = "empresas_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @OneToMany(targetEntity = Scholarship.class, mappedBy = "project")
    private List<Scholarship> scholarshipList = new ArrayList<>();

    public Project() {
    }

    public Project(Integer id, String name, @NotNull LocalDate start, @NotNull LocalDate end) {
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

    public String getYearNotice() {
        return yearNotice;
    }

    public void setYearNotice(String yearNotice) {
        this.yearNotice = yearNotice;
    }

    public Person getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Person advisor) {
        this.advisor = advisor;
    }

    public List<Person> getCoAdvisors() {
        return coAdvisors;
    }

    public void setCoAdvisors(List<Person> coAdvisors) {
        this.coAdvisors = coAdvisors;
    }

    // Helper methods for co-advisors
    public void addCoAdvisor(Person coAdvisor) {
        if (!this.coAdvisors.contains(coAdvisor)) {
            this.coAdvisors.add(coAdvisor);
            coAdvisor.getCoAdvisedProjects().add(this);
        }
    }

    public void removeCoAdvisor(Person coAdvisor) {
        this.coAdvisors.remove(coAdvisor);
        coAdvisor.getCoAdvisedProjects().remove(this);
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