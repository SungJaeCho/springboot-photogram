package com.cos.photogramstart.domain.user;

// JPA (자바로 데이터를 영구적을 저장[DB]할 수 있는 API를 제공)

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private Long id;

    private String username;
    private String password;

    private String name;
    private String website;
    private String bio; // 자기소
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    private LocalDateTime createDate;

    @PrePersist // DB Insert되기 직전에 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
