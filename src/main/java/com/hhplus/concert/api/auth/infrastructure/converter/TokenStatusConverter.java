package com.hhplus.concert.api.auth.infrastructure.converter;

import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class TokenStatusConverter implements AttributeConverter<TokenStatus, String> {

    @Override
    public String convertToDatabaseColumn(TokenStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public TokenStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(TokenStatus.class).stream()
                      .filter(e -> e.getCode().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchElementException::new);
    }
}
