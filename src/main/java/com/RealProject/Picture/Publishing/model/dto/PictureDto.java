package com.RealProject.Picture.Publishing.model.dto;

import com.RealProject.Picture.Publishing.model.entity.User;
import com.RealProject.Picture.Publishing.model.enums.Category;
import com.RealProject.Picture.Publishing.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {

    private String title;

    private String description;

    private Category category;

    private Status status;

    private byte[] image;

    private String imageType;

    private LocalDateTime createdAt;

    private User user;

}
