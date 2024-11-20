package org.example.expert.testuser.dto;

import lombok.Getter;
import org.example.expert.testuser.entity.TestUser;

@Getter
public class TestUserResponse {
    private Long id;
    private String name;
    private int age;
    public TestUserResponse(TestUser byName) {
        this.id = byName.getId();
        this.name = byName.getName();
        this.age = byName.getAge();
    }
}
