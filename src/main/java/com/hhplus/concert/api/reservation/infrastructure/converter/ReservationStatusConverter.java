package com.hhplus.concert.api.reservation.infrastructure.converter;

import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class ReservationStatusConverter implements AttributeConverter<ReservationStatus, String> {

    @Override
    public String convertToDatabaseColumn(ReservationStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ReservationStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ReservationStatus.class).stream()
                      .filter(e -> e.getCode().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchElementException::new);
    }
}
