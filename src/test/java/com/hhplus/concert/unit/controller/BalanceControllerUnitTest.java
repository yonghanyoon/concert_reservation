package com.hhplus.concert.unit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.balance.domain.entity.Balance;
import com.hhplus.concert.api.balance.presentation.BalanceController;
import com.hhplus.concert.api.balance.presentation.dto.request.ChargeReqVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(BalanceController.class)
public class BalanceControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BalanceService balanceService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("잔액 충전 테스트")
    @Test
    public void putChargeTest() throws Exception {
        // given
        ChargeReqVo reqVo = new ChargeReqVo(1L, 1000L);
        Balance balance = Balance.builder()
            .userId(1L)
            .amount(1000L)
            .build();

        // when
        when(balanceService.putCharge(balance)).thenReturn(balance);

        // then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/balance/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqVo)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value(1L))
            .andExpect(jsonPath("$.amount").value(1000L));
    }

    @DisplayName("잔액 조회 테스트")
    @Test
    public void getBalanceTest() throws Exception {
        // given
        Long userId = 1L;
        Balance balance = Balance.builder()
            .userId(1L)
            .amount(1000L)
            .build();

        // when
        when(balanceService.getBalance(userId)).thenReturn(balance);

        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/balance/{userId}", userId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.amount").value(1000L));
    }

}
