package org.example.expert.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoSearchResponseDto {
    private String title;
    private Long managerCount;
    private Long commentCount;
}
