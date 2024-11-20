package org.example.expert.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_time", nullable = false)
    private LocalDateTime requestTime;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "message")
    private String message;

    @Column(name = "user_id")
    private Long userId;

}
