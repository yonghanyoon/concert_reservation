package com.hhplus.concert.payment.domain.type;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    PAYMENT("1", "결제"),
    CHARGE("2", "충전");

    private final String code;
    private final String desc;

    public static PaymentType of(String code) {
        return Arrays.stream(PaymentType.values())
            .filter(paymentType -> code.equals(paymentType.getCode()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("[PaymentType] 존재하지 않는 코드입니다. { " + code + " }"));
    }
}
