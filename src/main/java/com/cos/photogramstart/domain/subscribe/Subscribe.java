package com.cos.photogramstart.domain.subscribe;

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
        uniqueConstraints = { //유니크 제약조건 지금구독 테이블의 경우 같은 fromuserid, touserid는 필요가없음.
                @UniqueConstraint(
                        name="subscribe_uk",
                        columnNames = {"fromUserId","toUserId"} //실제 데이터베이스 테이블 컬럼명
                )
        }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne // Subscribe N : 1 User
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist // DB Insert되기 직전에 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
