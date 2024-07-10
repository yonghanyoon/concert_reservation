package com.hhplus.concert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.balance.domain.Balance;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BalanceServiceTest {

    private BalanceService balanceService = Mockito.mock(BalanceService.class);

    @DisplayName("잔액 충전 테스트")
    @Test
    public void putChargeTest() {
        // given
        Balance balance = new Balance();
        balance.setBalanceId(1L);
        balance.setUserId(1L);
        balance.setAmount(1000L);
        balance.setModDt(LocalDateTime.of(2024, 6, 28, 12, 0));

        // when
        when(balanceService.putCharge(balance)).thenReturn(balance);

        // then
        assertThat(balanceService.putCharge(balance).getBalanceId()).isEqualTo(1L);
        assertThat(balanceService.putCharge(balance).getUserId()).isEqualTo(1L);
        assertThat(balanceService.putCharge(balance).getAmount()).isEqualTo(1000L);
        assertThat(balanceService.putCharge(balance).getModDt()).isEqualTo("2024-06-28T12:00:00");
    }

    @DisplayName("잔액 조회 테스트")
    @Test
    public void getBalanceTest() {
        // given
        Long userId = 1L;
        Balance balance = new Balance();
        balance.setBalanceId(1L);
        balance.setUserId(userId);
        balance.setAmount(1000L);
        balance.setModDt(LocalDateTime.of(2024, 6, 28, 12, 0));

        // when
        when(balanceService.getBalance(userId)).thenReturn(balance);

        // then
        assertThat(balanceService.getBalance(userId).getBalanceId()).isEqualTo(1L);
        assertThat(balanceService.getBalance(userId).getUserId()).isEqualTo(1L);
        assertThat(balanceService.getBalance(userId).getAmount()).isEqualTo(1000L);
        assertThat(balanceService.getBalance(userId).getModDt()).isEqualTo("2024-06-28T12:00:00");
    }
}
