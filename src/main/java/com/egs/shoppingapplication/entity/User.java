package com.egs.shoppingapplication.entity;

import com.egs.shoppingapplication.entity.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    private static final long serialVersionUID = -5841255965871412565L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @OneToOne
//    @JoinColumn(nullable = false)
//    @MapsId
//    @JsonIgnore
//    private Order order;

    private String firstName;

    private String lastName;

    @Column(name = "encryted_password", length = 128, nullable = false)
    @JsonIgnore
    private String password;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    private String address;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<ProductReview> reviews;

    public User() {
    }

    public User(String firstName, String lastName, boolean active, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}