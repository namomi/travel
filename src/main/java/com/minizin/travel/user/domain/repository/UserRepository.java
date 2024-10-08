package com.minizin.travel.user.domain.repository;

import static com.minizin.travel.user.domain.enums.UserErrorCode.*;

import com.minizin.travel.global.exception.CustomException;
import com.minizin.travel.global.exception.ErrorCode;
import com.minizin.travel.user.domain.entity.UserEntity;
import com.minizin.travel.user.domain.enums.LoginType;
import com.minizin.travel.user.domain.exception.CustomUserException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByEmailAndLoginType(String email, LoginType loginType);

    default UserEntity findByUsernameOrThrow(String username) {
        return findByUsername(username)
            .orElseThrow(() -> new CustomUserException(USER_NOT_FOUND));
    }

}
