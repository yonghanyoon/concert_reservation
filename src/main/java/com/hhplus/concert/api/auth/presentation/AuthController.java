package com.hhplus.concert.api.auth.presentation;

import com.hhplus.concert.api.auth.presentation.dto.request.AuthTokenPostReqVo;
import com.hhplus.concert.api.auth.presentation.dto.response.AuthTokenPostResVo;
import com.hhplus.concert.api.auth.infrastructure.type.TokenStatus;
import com.hhplus.concert.api.auth.presentation.dto.request.AuthTokenGetReqVo;
import com.hhplus.concert.api.auth.presentation.dto.response.AuthTokenGetResVo;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    // 유저 대기열 토큰 발급 API
    @PostMapping("/token")
    public ResponseEntity<AuthTokenPostResVo> postToken(AuthTokenPostReqVo reqVo) {
        return ResponseEntity.ok(new AuthTokenPostResVo(UUID.randomUUID().toString()));
    }

    // 유저 대기열 토큰 조회 API
    @GetMapping("/token/{uuid}")
    public ResponseEntity<AuthTokenGetResVo> getToken(@PathVariable String uuid) {
        return ResponseEntity.ok(new AuthTokenGetResVo(1, TokenStatus.RESERVED, LocalDateTime.now().plusMinutes(10)));
    }
}
