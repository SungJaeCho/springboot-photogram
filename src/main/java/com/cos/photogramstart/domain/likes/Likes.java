package com.cos.photogramstart.domain.likes;

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
@Table(
        uniqueConstraints = { //유니크 제약조건 지금구독 테이블의 경우 같은 imageId, userId 필요가없음. 둘다 같은 값으로 중복된건 허용할 수 없음.
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"imageId","userId"} //실제 데이터베이스 테이블 컬럼명
                )
        }
)
public class Likes { //like로는 테이블 생성 불가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name="imageId")
    @ManyToOne
    private Image image; // 1

    @JoinColumn(name="userId")
    @ManyToOne
    private User user;

    private LocalDateTime createDate;

    @PrePersist // DB Insert되기 직전에 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
