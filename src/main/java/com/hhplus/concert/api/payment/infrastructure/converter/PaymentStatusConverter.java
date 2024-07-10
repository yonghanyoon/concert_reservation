package com.hhplus.concert.api.payment.infrastructure.converter;

import com.hhplus.concert.api.payment.domain.type.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(PaymentStatus.class).stream()
                      .filter(e -> e.getCode().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchElementException::new);
    }
}
