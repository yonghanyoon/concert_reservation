package com.hhplus.concert.api.reservation.domain.type.converter;

import com.hhplus.concert.api.reservation.domain.type.ReservationEventStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class ReservationEventStatusConverter implements AttributeConverter<ReservationEventStatus, String> {

    @Override
    public String convertToDatabaseColumn(ReservationEventStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ReservationEventStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ReservationEventStatus.class).stream()
                      .filter(e -> e.getCode().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchElementException::new);
    }
}
