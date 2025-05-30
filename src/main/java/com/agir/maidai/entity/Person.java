package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pessoas")
public class Person extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Nome não pode estar em branco.")
    @Column(name = "nome")
    private String name;

    @Pattern(regexp = "^(|\\d+)$", message = "Telefone deve conter apenas números.")
    @Column(name = "telefone", nullable = true )
    private String phone;

    @Email(message = "Email deve ser válido.")
    @Column(name = "email", nullable = true)
    private String email;

//    @OneToOne(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private Advisor advisor;

    @ManyToMany
    @JoinTable(
        name = "pessoas_ppg",
        joinColumns = @JoinColumn(name = "pessoas_id"),
        inverseJoinColumns =    @JoinColumn(name = "ppg_id")
    )
    private List<PPG> ppgList = new ArrayList<>();

    @NotNull(message = "Tipo de pessoas precisa ser definido.")
    @ManyToOne
    @JoinColumn(name = "tipos_pessoa_id", referencedColumnName = "id")
    private PersonType personType;

    @OneToOne(targetEntity = Scholarship.class, mappedBy = "person", cascade = {CascadeType.REMOVE})
    private Scholarship scholarship;

    public Person() {
    }

    public Person(Integer id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone =(phone == null || phone.trim().isEmpty()) ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = (email == null || email.trim().isEmpty()) ? null : email.trim();
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public List<PPG> getPpgList() {
        return ppgList;
    }

    public void setPpgList(List<PPG> ppgList) {
        this.ppgList = ppgList;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    // Helper methods for managing the relationship
    public void addPpg(PPG ppg) {
        if(!this.ppgList.contains(ppg)) {
            this.ppgList.add(ppg);
            ppg.getPersonList().add(this);
        }
    }

    public void removePpg(PPG ppg) {
        this.ppgList.remove(ppg);
        ppg.getPersonList().remove(this);
    }

    //    public Advisor getAdvisor() {
//        return advisor;
//    }
//
//    public void setAdvisor(Advisor advisor) {
//        this.advisor = advisor;
//    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
