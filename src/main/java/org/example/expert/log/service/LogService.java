package org.example.expert.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.log.entity.Log;
import org.example.expert.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logRequest(String action,  String message, Long userId) {
        Log log = new Log();
        log.setRequestTime(LocalDateTime.now());
        log.setAction(action);
        log.setMessage(message);
        log.setUserId(userId);
        logRepository.save(log);
    }
}
