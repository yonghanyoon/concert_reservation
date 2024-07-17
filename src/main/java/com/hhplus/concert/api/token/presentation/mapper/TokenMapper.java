package com.hhplus.concert.api.token.presentation.mapper;

import com.hhplus.concert.api.token.domain.entity.QueueToken;
import com.hhplus.concert.api.token.presentation.dto.request.TokenPostReqDTO;
import com.hhplus.concert.api.token.presentation.dto.response.TokenGetResDTO;
import com.hhplus.concert.api.token.presentation.dto.response.TokenPostResDTO;

public class TokenMapper {

    public static QueueToken toEntity(TokenPostReqDTO dto) {
        if (dto == null) {
            return null;
        }
        QueueToken queueToken = new QueueToken().builder()
            .userId(dto.getUserId())
            .build();
        return queueToken;
    }

    public static TokenPostResDTO toDtoFromPost(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        TokenPostResDTO dto = new TokenPostResDTO().builder()
            .uuid(queueToken.getUuid())
            .build();
        return dto;
    }

    public static TokenGetResDTO toDtoFromGet(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        TokenGetResDTO dto = new TokenGetResDTO().builder()
            .position(queueToken.getPosition())
            .tokenStatus(queueToken.getTokenStatus())
            .expirationTime(queueToken.getExpirationTime())
            .build();
        return dto;
    }

}
