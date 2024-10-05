package com.RealProject.Picture.Publishing.model.entity;

import com.RealProject.Picture.Publishing.model.enums.Category;
import com.RealProject.Picture.Publishing.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name" , nullable = false)
    private String title;

    @Column(name = "description" , length = 254)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category" , nullable = false )
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false )
    private Status status;


    @Column(name = "image_data" ,nullable = false ,columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "image_type" , nullable = false)
    private String imageType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false ,referencedColumnName = "id")
    private User user;

}
