package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);

    @Query("SELECT ut.userId FROM UserToken ut WHERE ut.token = :token")
    String findUserIdByToken(@Param("token") String token);

    @Query("SELECT COUNT(ut) > 0 FROM UserToken ut WHERE ut.token = :token")
    boolean existsByToken(@Param("token") String token);

    @Modifying
    @Query("UPDATE UserToken ut SET ut.token = :token, ut.lastUpdateDate = :lastUpdateDate WHERE ut.userId = :userId")
    void updateToken(@Param("token") String token, @Param("userId") String userId, @Param("lastUpdateDate") LocalDateTime lastUpdateDate);

    @Modifying
    @Query("INSERT INTO UserToken (token, userId, createDate, lastUpdateDate) VALUES (:token, :userId, :createDate, :lastUpdateDate)")
    void insertToken(@Param("token") String token, @Param("userId") String userId, @Param("createDate") LocalDateTime createDate, @Param("lastUpdateDate") LocalDateTime lastUpdateDate);
}
