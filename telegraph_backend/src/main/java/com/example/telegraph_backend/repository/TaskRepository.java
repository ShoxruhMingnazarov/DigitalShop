package com.example.telegraph_backend.repository;

import com.example.telegraph_backend.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    TaskEntity findTaskEntityById(UUID id);

    @Query(value = "select t from tasks t order by t.createdDate asc")
    List<TaskEntity> getAllByData();



    List<TaskEntity> findTaskEntitiesByTitleOrderByTitleAsc(String title);

    Optional<TaskEntity> findById(String l);
}
