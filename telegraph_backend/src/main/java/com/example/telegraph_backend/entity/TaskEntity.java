package com.example.telegraph_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskEntity extends BaseEntity{

    private String title;

    private String author;


    private String description;

    private String link;

    @ManyToOne
    private UserEntity user;

}
