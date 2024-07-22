package com.hhplus.concert.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.balance.domain.entity.Balance;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class BalanceServiceIntegrationTest {

    @Autowired
    private BalanceService balanceService;

    @DisplayName("잔액 조회 테스트")
    @Test
    void get_balance_test() {
        // given
        Long userId = 1L;
        // when
        Balance balance = balanceService.getBalance(userId);
        // then
        assertEquals(balance.getUserId(), userId);
        assertEquals(balance.getAmount(), 7100L);
    }

    @DisplayName("잔액 조회 존재하지 않는 사용자 실패 테스트")
    @Test
    void get_balance_user_not_found_fail_test() {
        // given
        Long userId = 999L;
        // when
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> balanceService.getBalance(userId));
        // then
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals("존재하지 않는 사용자", exception.getMessage());
    }

    @DisplayName("잔액 충전 테스트")
    @Test
    void put_charge_test() {
        // given
        Balance balance = Balance.builder()
            .userId(1L)
            .amount(7000L)
            .build();
        // when
        balanceService.putCharge(balance);
        // then
        assertEquals(balance.getUserId(), 1L);
        assertEquals(balance.getAmount(), 14100);
    }

    @DisplayName("잔액 충전 존재하지 않는 사용자 실패 테스트")
    @Test
    void put_charge_fail_test() {
        // given
        Balance balance = Balance.builder()
                                 .userId(99L)
                                 .amount(7000L)
                                 .build();
        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> balanceService.putCharge(balance));
        // then
        assertEquals("존재하지 않는 사용자", exception.getMessage());
    }

    @DisplayName("잔액 충전 동시성 테스트")
    @Test
    public void concurrent_charge_test() throws InterruptedException {
        // given
        Long userId = 18L;
        Long amount = 7000L;
        Balance balance = Balance.builder()
                                 .userId(userId)
                                 .amount(amount)
                                 .build();
        Balance beforeBalance = balanceService.getBalance(userId);

        int numberOfExecute = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfExecute);

        for (int i = 0; i < numberOfExecute; i++) {
            executorService.execute(() -> {
                try {
                    balanceService.putCharge(balance).getAmount();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Balance afterBalance = balanceService.getBalance(userId);

        assertEquals(beforeBalance.getAmount() + amount, afterBalance.getAmount());
    }

    @DisplayName("결제 잔액 차감 동시성 테스트")
    @Test
    public void concurrent_use_test() throws InterruptedException {
        // given
        Long userId = 18L;
        Long amount = 7000L;
        Balance beforeBalance = balanceService.getBalance(userId);

        int numberOfExecute = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfExecute);

        for (int i = 0; i < numberOfExecute; i++) {
            executorService.execute(() -> {
                try {
                    balanceService.useBalance(userId, amount);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Balance afterBalance = balanceService.getBalance(userId);

        assertEquals(beforeBalance.getAmount() - amount, afterBalance.getAmount());
    }
}
