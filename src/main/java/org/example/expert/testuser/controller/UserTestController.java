package org.example.expert.testuser.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.testuser.dto.TestUserResponse;
import org.example.expert.testuser.service.UserDataGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTestController {

    private final UserDataGeneratorService service;

    @PostMapping("/user/data/generate")
    public String userDataGenerator(){
        service.generateAndInsertUsers(1000000);
        return "success";
    }

    @GetMapping("/user/data")
    public TestUserResponse userData(@RequestParam int name){
        return service.getUserData(name);
    }

}
