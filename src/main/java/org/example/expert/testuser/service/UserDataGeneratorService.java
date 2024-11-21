package org.example.expert.testuser.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.testuser.dto.TestUserResponse;
import org.example.expert.testuser.entity.TestUser;
import org.example.expert.testuser.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDataGeneratorService {


    private final UserTestRepository userTestRepository;


    public void generateAndInsertUsers(int count) {
        List<TestUser> users = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            TestUser user = new TestUser();
            user.setId((long) i);
            user.setName("User" + i);
            user.setAge(20 + (i % 30)); // 20~49세
            users.add(user);
        }

        userTestRepository.bulkInsertUsers(users);
    }

    public TestUserResponse getUserData(String name) {
        TestUser byName = userTestRepository.findByName(name);
        return new TestUserResponse(byName);
    }
}
