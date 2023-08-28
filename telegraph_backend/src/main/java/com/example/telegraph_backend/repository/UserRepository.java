package com.example.telegraph_backend.repository;

import com.example.telegraph_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
     Optional<UserEntity> findUserEntityByUsername(String username);

     UserEntity findUserEntitiesById(UUID id);

}
