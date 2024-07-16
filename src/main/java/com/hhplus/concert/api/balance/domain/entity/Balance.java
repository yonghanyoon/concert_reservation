package com.hhplus.concert.api.balance.domain.entity;

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
@Table(name = "BALANCE")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BALANCE_ID")
    private Long balanceId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "MOD_DT")
    private LocalDateTime modDt;

    public void putCharge(Long balanceId, Long amount, LocalDateTime modDt) {
        this.balanceId = balanceId;
        this.amount = amount;
        this.modDt = modDt;
    }

    public void useAmount(Long amount, LocalDateTime modDt) {
        this.amount = amount;
        this.modDt = modDt;
    }
}
