package com.hhplus.concert.api.payment.infrastructure.type;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    SUCCESS("1", "성공"),
    FAIL("2", "실패");

    private final String code;
    private final String desc;

    public static PaymentStatus of(String code) {
        return Arrays.stream(PaymentStatus.values())
            .filter(paymentType -> code.equals(paymentType.getCode()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("[PaymentType] 존재하지 않는 코드입니다. { " + code + " }"));
    }
}
