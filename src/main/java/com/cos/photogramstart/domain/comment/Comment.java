package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER) //manyToOne의 기본 fetchType
    private User user;

    @JoinColumn(name = "imageId")
    @ManyToOne(fetch = FetchType.EAGER) //manyToOne의 기본 fetchType
    private Image image;

    private LocalDateTime createDate;

    @PrePersist // DB Insert되기 직전에 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
