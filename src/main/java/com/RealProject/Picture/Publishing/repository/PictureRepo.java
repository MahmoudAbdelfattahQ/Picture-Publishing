package com.RealProject.Picture.Publishing.repository;

import com.RealProject.Picture.Publishing.model.entity.Picture;
import com.RealProject.Picture.Publishing.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {

    List<Picture> findByStatus(Status status);
    Collection<Picture> findByUserId(Long userId);


}
