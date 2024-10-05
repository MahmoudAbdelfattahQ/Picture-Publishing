package com.RealProject.Picture.Publishing.model.trasnformations;


public class Transformations {



    private Transformations(){

    }
//
//    public static User toUser(UserDto userDto){
//        Role roleObj =new Role();
//        roleObj.setRoleName("ROLE_USER");
//        List<Role> roles = new ArrayList<>();
//        roles.add(roleObj);
//
//       return new User(UUID.randomUUID(),
//               userDto.getUserName(),
//               passwordEncoder.encode(userDto.getPassword()), //Encode the password
//               userDto.getEmail(),roles);    // User role as default
//    }
//
//    public static UserDto toUserDto(User user){
//        return new UserDto(user.getUsername(),user.getPassword(),user.getEmail());
//            }
//
//    public static Picture toPicture(PictureDto pictureDto){
//       return Picture.builder()
//               .id(UUID.randomUUID())
//               .title(pictureDto.getTitle())
//               .description(pictureDto.getDescription())
//               .category(pictureDto.getCategory())
//               .status(pictureDto.getStatus())
//               .size(pictureDto.getSize())
//               .height(pictureDto.getHeight())
//               .width(pictureDto.getWidth())
//               .user(pictureDto.getUser())
//               .build();
//     }
//
//    public static PictureDto toPictureDto(Picture picture){
//        return  PictureDto.builder()
//                .title(picture.getTitle())
//                .description(picture.getDescription())
//                .category(picture.getCategory())
//                .status(picture.getStatus())
//                .size(picture.getSize())
//                .height(picture.getHeight())
//                .width(picture.getWidth())
//                .user(picture.getUser())
//                .build();
//     }
//

}
