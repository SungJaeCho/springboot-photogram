package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            return new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId==principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState==1);
        dto.setSubscribeCount(subscribeCount);

        // 좋아요 카운트 추가하기
        userEntity.getImages().forEach(image->{
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }


    @Transactional
    public User 회원수정(int id, User user){
        // 1. 영속화
        // 1.무조건찾았다 get() 2.못찾았어 익셉션 발동 orElseThrow()
//        User userEntity = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//            @Override
//            public IllegalArgumentException get() {
//                return new IllegalArgumentException("찾을수 없는 아이디 입니다.");
//            }
//        });
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
                return new CustomValidationApiException("찾을수 없는 아이디 입니다.");
        });

        // 2. 영속화된 오브젝트를 수정 -더티체킹 (업데이트완료)
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
        //더티체킹이 발생되어 업데이트가 완료됨
    }
}
