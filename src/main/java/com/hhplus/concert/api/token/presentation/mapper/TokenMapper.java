package com.hhplus.concert.api.token.presentation.mapper;

import com.hhplus.concert.api.token.domain.entity.QueueToken;
import com.hhplus.concert.api.token.presentation.dto.request.TokenReqDTO;
import com.hhplus.concert.api.token.presentation.dto.response.TokenResDTO;

public class TokenMapper {

    public static QueueToken toEntity(TokenReqDTO dto) {
        if (dto == null) {
            return null;
        }
        QueueToken queueToken = new QueueToken().builder()
            .userId(dto.getUserId())
            .build();
        return queueToken;
    }

    public static TokenResDTO toDto(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        TokenResDTO dto = new TokenResDTO().builder()
                                           .userId(queueToken.getUserId())
                                           .position(queueToken.getPosition())
                                           .waitingTime(queueToken.getWaitingTime())
                                           .build();
        return dto;
    }

}
