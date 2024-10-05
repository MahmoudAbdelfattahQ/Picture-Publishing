package com.RealProject.Picture.Publishing.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100 , nullable = false)
    private String username;

    @Column(name = "password", length = 254 , nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;


    @ManyToMany(cascade = CascadeType.REMOVE ,fetch = FetchType.EAGER)
    @JoinTable(name =  "users_roles" ,
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Picture> pictures;
}