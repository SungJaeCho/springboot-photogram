package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption; // 사진내용
    private String postImageUrl; //사진을 전송받아서 그 사진을 서버 특정폴더에 저장 - DB에 그 저장된 경로를 insert

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    // 이미지 좋아요. 개발예정

    // 이미지 댓글. 개발예정

    private LocalDateTime createDate;

    @PrePersist // DB Insert되기 직전에 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
