package com.hhplus.concert.api.auth.presentation.mapper;

import com.hhplus.concert.api.auth.domain.QueueToken;
import com.hhplus.concert.api.auth.presentation.dto.request.AuthTokenPostReqDTO;
import com.hhplus.concert.api.auth.presentation.dto.response.AuthTokenGetResDTO;
import com.hhplus.concert.api.auth.presentation.dto.response.AuthTokenPostResDTO;
import java.util.UUID;

public class QueueTokenMapper {

    public static QueueToken toEntity(AuthTokenPostReqDTO dto) {
        if (dto == null) {
            return null;
        }
        QueueToken queueToken = new QueueToken();
        queueToken.setUserId(dto.getUserId());
        return queueToken;
    }

    public static AuthTokenPostResDTO toDtoFromPost(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        AuthTokenPostResDTO dto = new AuthTokenPostResDTO();
        dto.setUuid(queueToken.getUuid());
        return dto;
    }

    public static AuthTokenGetResDTO toDtoFromGet(QueueToken queueToken) {
        if (queueToken == null) {
            return null;
        }
        AuthTokenGetResDTO dto = new AuthTokenGetResDTO();
        dto.setPosition(queueToken.getPosition());
        dto.setTokenStatus(queueToken.getTokenStatus());
        dto.setExpirationTime(queueToken.getExpirationTime());
        return dto;
    }

}
