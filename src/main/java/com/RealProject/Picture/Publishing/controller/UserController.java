package com.RealProject.Picture.Publishing.controller;


import com.RealProject.Picture.Publishing.model.dto.PictureDto;
import com.RealProject.Picture.Publishing.model.entity.Picture;
import com.RealProject.Picture.Publishing.service.PictureService;
import com.RealProject.Picture.Publishing.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;


@Slf4j
@Controller
@RequestMapping()
public class UserController {

    private final PictureService PictureServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(PictureService PictureServiceImpl, UserServiceImpl userServiceImpl) {
        this.PictureServiceImpl = PictureServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/home") // for Guests without signIn to show all pictures
    public String showHome(Model model) {
        log.info("showHome");
        Collection<Picture> pictures = PictureServiceImpl.getAllAcceptedImage();

        log.info("picturesList is listed");
        pictures.forEach(picture -> System.out.println(picture.getTitle() + " id: " + picture.getId()));

        model.addAttribute("pictures", pictures);
        return "home";

    }
    @GetMapping("/pictures/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        log.info("getImage with id {}", id);
        Picture picture = PictureServiceImpl.getImageById(id).get();
        HttpHeaders headers = new HttpHeaders();
        String imageType = picture.getImageType(); // This should be stored in the database
        MediaType mediaType = switch (imageType.toLowerCase()) {
            case "image/png" -> MediaType.IMAGE_PNG;
            case "image/jpeg", "image/jpg" -> MediaType.IMAGE_JPEG;
            case "image/gif" -> MediaType.IMAGE_GIF;
            default -> throw new IllegalArgumentException("Unsupported image type: " + imageType);
        };

        headers.setContentType(mediaType);
        return new ResponseEntity<>(picture.getImage(), headers, HttpStatus.OK);
    }

    @GetMapping("/systems") // for admin users for accept or reject the pictures
    public String systems(Model model) {
       Collection<Picture> pictures = PictureServiceImpl.getAllImage();
       model.addAttribute("pictures", pictures);

        return "system";

    }

    @PostMapping("/action/{id}/accept")
    public String accept( @PathVariable Long id ) {

        PictureServiceImpl.acceptImageById(id);
        log.info("picture accepted");
        return "redirect:/systems";
    }

    @PostMapping("/action/{id}/reject")
    public String reject( @PathVariable Long id ) {

        PictureServiceImpl.rejectImageById(id);
        log.info("picture rejected");
        return "redirect:/systems";

    }
    @PostMapping("/action/{id}/delete")
    public String delete( @PathVariable Long id ) {

        PictureServiceImpl.deleteImageById(id);
        log.info("picture delete");
        return "redirect:/systems";

    }

    @GetMapping("/userDashboard")  // for user to load pictures
    public String userDashboard(Model model, Authentication authentication) {

        String username = authentication.getName();
        Long userId = userServiceImpl.findByUserName(username).getId();
        Collection<Picture> pictureList = PictureServiceImpl.getAllImageByUserId(userId);
       pictureList.forEach(
               picture -> System.out.println(picture.getStatus()));
        model.addAttribute("pictures", pictureList);
        model.addAttribute("pictureDto", new PictureDto());

        return "userDashboard";

    }
    @PostMapping("/userDashboard/upload")
    public String upload(@ModelAttribute("pictureDto") PictureDto pictureDto,
                         @RequestParam("imag") MultipartFile file, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the authenticated user

        try {
            if (file.isEmpty() || !isValid(file)) {
                model.addAttribute("error", "Invalid uploaded file");
                log.info("User try to upload an Invalid file ! ");
            }
            PictureServiceImpl.saveImage(file, pictureDto, username);
            return "redirect:/userDashboard";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            log.error("User [{}] encountered an unexpected error: {}", username, e.getMessage());
            return "redirect:/userDashboard/list";

        }
    }
    private boolean isValid(MultipartFile file) {

        String type = file.getContentType();
        long size = file.getSize()/(1024*1024);
        assert type != null;
        return type.equals("image/png") || type.equals("image/jpeg") || type.equals("image/gif") && size <= 2;
    }

}
