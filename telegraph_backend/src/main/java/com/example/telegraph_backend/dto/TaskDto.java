package com.example.telegraph_backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {

    @NotBlank(message = "title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "description cannot be blank")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "author  cannot be blank")
    @Column(nullable = false)
    private String author;
}
