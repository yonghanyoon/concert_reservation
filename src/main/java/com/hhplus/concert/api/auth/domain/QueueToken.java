package com.hhplus.concert.api.auth.domain;

import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "QUEUE_TOKEN")
public class QueueToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID")
    private Long tokenId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "TOKEN_STATUS")
    private TokenStatus tokenStatus;
    @Column(name = "EXPIRATION_TIME")
    private LocalDateTime expirationTime;
    @Column(name = "POSITION")
    private Integer position;

}
