package org.example.expert.testuser.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TestUser {
    private Long id;
    private String name;
    private int age;
}
