package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName =  uuid+ "_" + imageUploadDto.getFile().getOriginalFilename();
        System.out.println("이미지파일명 : " +imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);
        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
        //image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
//        Image imageEntity = imageRepository.save(image); imageEntity를 받을필요 없음.
        imageRepository.save(image);

//        System.out.println(imageEntity); //주석풀면 에러남 Image entity의 user항목이 image와 서로 무한참조가 일어났기때문에
    }
}
