package com.hhplus.concert.api.concert.infrastructure.converter;

import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class SeatStatusConverter implements AttributeConverter<SeatStatus, String> {

    @Override
    public String convertToDatabaseColumn(SeatStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public SeatStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(SeatStatus.class).stream()
                      .filter(e -> e.getCode().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchElementException::new);
    }
}
