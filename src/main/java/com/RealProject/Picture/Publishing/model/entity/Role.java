package com.RealProject.Picture.Publishing.model.entity;

import com.RealProject.Picture.Publishing.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;


    @Column(name ="role_name" ,nullable = false )
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;



}