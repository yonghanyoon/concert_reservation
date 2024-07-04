package com.hhplus.concert.presentation;

import com.hhplus.concert.infrastructure.type.TokenStatus;
import com.hhplus.concert.presentation.dto.request.AuthTokenGetReqVo;
import com.hhplus.concert.presentation.dto.request.AuthTokenPostReqVo;
import com.hhplus.concert.presentation.dto.response.AuthTokenGetResVo;
import com.hhplus.concert.presentation.dto.response.AuthTokenPostResVo;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/token")
    public ResponseEntity<AuthTokenGetResVo> getToken(AuthTokenGetReqVo reqVo) {
        return ResponseEntity.ok(new AuthTokenGetResVo(1, TokenStatus.RESERVED, LocalDateTime.now().plusMinutes(10)));
    }
}
