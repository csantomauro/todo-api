package com.cs.todo_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean completed;
}
