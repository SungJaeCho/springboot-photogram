package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
// 어노테이션이 없어도 IOC등록이 자동으로됨 JpaRepository상송시
public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA Query Creation
    User findByUsername(String username);

}
