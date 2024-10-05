package com.RealProject.Picture.Publishing.service;


import com.RealProject.Picture.Publishing.model.dto.PictureDto;
import com.RealProject.Picture.Publishing.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface PictureService {

   void saveImage(MultipartFile file, PictureDto pictureDto ,String username) throws IOException;

   Optional<Picture> getImageById(Long id);

   Collection<Picture> getAllImagesByUsername(String username);

   Collection<Picture> getAllAcceptedImage();

   Collection<Picture> getAllImageByUserId(Long id);
  Collection<Picture> getAllImage();

  void acceptImageById(Long id);
  void rejectImageById(Long id);
  void deleteImageById(Long id);
}
