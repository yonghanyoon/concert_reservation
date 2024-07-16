package com.hhplus.concert.api.token.domain.entity;

import com.hhplus.concert.api.token.domain.type.TokenStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
    private Long position;
    @Column(name = "CREATE_DT")
    private LocalDateTime createDt;

    public void updatePosition(Long position) {
        this.position = position;
    }

    public void updateAvailable(TokenStatus tokenStatus, Long position, LocalDateTime expirationTime) {
        this.tokenStatus = tokenStatus;
        this.position = position;
        this.expirationTime = expirationTime;
    }

    public void createToken(Long tokenId) {
        this.tokenId = tokenId;
    }

    public void reservedToken(String uuid, TokenStatus tokenStatus, LocalDateTime createDt) {
        this.uuid = uuid;
        this.tokenStatus = tokenStatus;
        this.createDt = createDt;
    }

    public void updateExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void updateExpired(TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
