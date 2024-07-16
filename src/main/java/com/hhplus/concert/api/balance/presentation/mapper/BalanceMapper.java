package com.hhplus.concert.api.balance.presentation.mapper;

import com.hhplus.concert.api.balance.domain.entity.Balance;
import com.hhplus.concert.api.balance.presentation.dto.request.ChargeReqVo;
import com.hhplus.concert.api.balance.presentation.dto.response.BalanceResVo;

public class BalanceMapper {

    public static Balance toEntity(ChargeReqVo dto) {
        if (dto == null) {
            return null;
        }
        Balance balance = new Balance().builder()
            .userId(dto.getUserId())
            .amount(dto.getAmount())
            .build();
        return balance;
    }

    public static BalanceResVo toDto(Balance balance) {
        if (balance == null) {
            return null;
        }
        BalanceResVo dto = new BalanceResVo().builder()
            .userId(balance.getUserId())
            .amount(balance.getAmount())
            .build();
        return dto;
    }

}
