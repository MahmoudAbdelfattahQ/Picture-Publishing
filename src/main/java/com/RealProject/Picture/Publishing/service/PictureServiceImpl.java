package com.RealProject.Picture.Publishing.service;

import com.RealProject.Picture.Publishing.model.dto.PictureDto;
import com.RealProject.Picture.Publishing.model.entity.Picture;
import com.RealProject.Picture.Publishing.model.entity.User;
import com.RealProject.Picture.Publishing.model.enums.Status;
import com.RealProject.Picture.Publishing.repository.PictureRepo;
import com.RealProject.Picture.Publishing.repository.UserRepoJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PictureServiceImpl  implements PictureService{


    private final UserRepoJPA UserRepo;
    private final PictureRepo pictureRepo;

    @Autowired
    public PictureServiceImpl(UserRepoJPA UserRepo, PictureRepo pictureRepo) {
        this.UserRepo = UserRepo;
        this.pictureRepo = pictureRepo;
    }

    @Override
    public void saveImage(MultipartFile file, PictureDto pictureDto ,String username) throws IOException {

        User user = UserRepo.findByUsername(username);

            if (username != null) {

                Picture picture = new Picture();
                picture.setTitle(pictureDto.getTitle());
                picture.setDescription(pictureDto.getDescription());
                picture.setImage(file.getBytes());
                picture.setImageType(file.getContentType());
                picture.setCategory(pictureDto.getCategory());
                picture.setStatus(Status.PENDING);// by default get the pending status
                picture.setCreatedAt(LocalDateTime.now());
                picture.setUser(user);
                pictureRepo.save(picture);
                log.info("Save Image successfully!");

            }

    }




    @Override
    public Optional<Picture> getImageById(Long id) {
       return pictureRepo.findById(id);
    }

    @Override
    public Collection<Picture> getAllImagesByUsername(String username) {
        User user = UserRepo.findByUsername(username);
        Collection<Picture> pictures = pictureRepo.findByUserId(user.getId());
        log.info("Get All Image By Username successfully!");
        return pictures;
    }

    @Override
    public List<Picture> getAllImage() {
        return pictureRepo.findAll();
    }

    @Override
    public Collection<Picture> getAllAcceptedImage() {

        return pictureRepo.findAll().stream()
                .filter(
                        picture ->
                                picture.getStatus().equals(Status.ACCEPTED))
                .collect(Collectors.toSet());

    }


    @Override
    public Collection<Picture> getAllImageByUserId(Long id){
        return pictureRepo.findByUserId(id);
    }

    @Override
    public  void acceptImageById(Long id){
    Picture picture = pictureRepo.findById(id).get();
    picture.setStatus(Status.ACCEPTED);
    pictureRepo.save(picture);

     log.info("Accept Image Successfully!");

    }
    @Override
    public  void rejectImageById(Long id){

    Picture  picture = pictureRepo.findById(id).get();

    picture.setStatus(Status.REJECTED);
    pictureRepo.save(picture);

    log.info("Reject Image Successfully!");


    }

    @Override
    public void deleteImageById(Long id){
        pictureRepo.deleteById(id);
        log.info("Delete Image Successfully!");
    }

}
