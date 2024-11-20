package org.example.expert.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/auth/health")
    public String healthCheck(){
        return "Server On";
    }
}
