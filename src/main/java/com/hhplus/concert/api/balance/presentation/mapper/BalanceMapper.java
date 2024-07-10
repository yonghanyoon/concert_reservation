package com.hhplus.concert.api.balance.presentation.mapper;

import com.hhplus.concert.api.balance.domain.Balance;
import com.hhplus.concert.api.balance.presentation.dto.request.ChargeReqVo;
import com.hhplus.concert.api.balance.presentation.dto.response.BalanceResVo;

public class BalanceMapper {

    public static Balance toEntity(ChargeReqVo dto) {
        if (dto == null) {
            return null;
        }
        Balance balance = new Balance();
        balance.setUserId(dto.getUserId());
        balance.setAmount(dto.getAmount());
        return balance;
    }

    public static BalanceResVo toDto(Balance balance) {
        if (balance == null) {
            return null;
        }
        BalanceResVo dto = new BalanceResVo();
        dto.setUserId(balance.getUserId());
        dto.setAmount(balance.getAmount());
        return dto;
    }

}
