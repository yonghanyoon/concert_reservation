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
        QueueToken queueToken = new QueueToken();
        queueToken.setUserId(dto.getUserId());
        return queueToken;
    }

    public static TokenPostResDTO toDtoFromPost(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        TokenPostResDTO dto = new TokenPostResDTO();
        dto.setUuid(queueToken.getUuid());
        return dto;
    }

    public static TokenGetResDTO toDtoFromGet(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        TokenGetResDTO dto = new TokenGetResDTO();
        dto.setPosition(queueToken.getPosition());
        dto.setTokenStatus(queueToken.getTokenStatus());
        dto.setExpirationTime(queueToken.getExpirationTime());
        return dto;
    }

}
