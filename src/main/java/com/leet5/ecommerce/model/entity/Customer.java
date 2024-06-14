package com.leet5.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name must be specified")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name must be specified")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Valid
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "First name must be specified") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name must be specified") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name must be specified") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name must be specified") String lastName) {
        this.lastName = lastName;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public @Valid List<Order> getOrders() {
        return orders;
    }

    public void setOrders(@Valid List<Order> orders) {
        this.orders = orders;
    }
}
