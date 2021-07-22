package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT A.* FROM image A\n" +
            "INNER JOIN subscribe B\n" +
            "ON A.userId = B.toUserId\n" +
            "WHERE B.fromUserId = :principalId ORDER BY A.id DESC"
            , countQuery = "SELECT COUNT(*) FROM image A\n" +
            "INNER JOIN subscribe B\n" +
            "ON A.userId = B.toUserId\n" +
            "WHERE B.fromUserId = :principalId"
            , nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "FROM image A\n" +
            "INNER JOIN likes B\n" +
            "ON A.id = B.imageId\n" +
            "GROUP BY A.id, A.caption, A.createDate, A.postImageUrl, A.userId\n" +
            "ORDER BY COUNT(B.imageId) DESC", nativeQuery = true)
    List<Image> mPopular();
}
