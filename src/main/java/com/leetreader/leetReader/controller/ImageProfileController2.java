package com.leetreader.leetReader.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetreader.leetReader.dto.ImageProfileDataDTO;
import com.leetreader.leetReader.dto.UserImageProfileDTO;
import com.leetreader.leetReader.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageProfileController2 {

    private final RestClient restClient;
    private final UserService userService;

    @Value("${imgbb.upload-link}")
    private String uri;
    @Value("${imgbb.key}")
    private String key;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload2")
    public ResponseEntity<String> imageProfile(@RequestParam("image") MultipartFile image) throws JsonProcessingException {

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("image", image.getResource());


        System.out.println("Amazing the image is uploaded 😊");
        var response = restClient.post()
                .uri(uri, uriBuilder -> uriBuilder.queryParam("key", key).build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipartBodyBuilder.build())
                .retrieve()
                .body(String.class);

        ImageProfileDataDTO profileDTO = new ObjectMapper().readValue(response, ImageProfileDataDTO.class);
        UserImageProfileDTO userImageProfileDTO = profileDTO.data();
        String imageProfileUrl = userImageProfileDTO.imageProfileUrl();
        System.out.println(imageProfileUrl);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUserImageProfile(username, imageProfileUrl);
        return ResponseEntity.ok("User Image profile updated");
    }

}
