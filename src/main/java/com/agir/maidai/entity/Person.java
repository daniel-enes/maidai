package com.agir.maidai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "pessoas")
public class Person extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "nome")
    private String name;

    @Column(name = "telefone", nullable = true )
    private String phone;

    @Email
    @Column(name = "email", nullable = true)
    private String email;

    //@OneToOne(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OneToOne(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Advisor advisor;

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

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

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
