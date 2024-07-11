package com.hhplus.concert.api.auth.presentation;

import com.hhplus.concert.api.auth.application.AuthService;
import com.hhplus.concert.api.auth.presentation.dto.request.AuthTokenPostReqDTO;
import com.hhplus.concert.api.auth.presentation.dto.response.AuthTokenPostResDTO;
import com.hhplus.concert.api.auth.presentation.dto.response.AuthTokenGetResDTO;
import com.hhplus.concert.api.auth.presentation.mapper.QueueTokenMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthController", description = "대기열 토큰 등록과 조회를 제공하기 위한 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 유저 대기열 토큰 발급 API
    @Operation(summary = "유저 대기열 토큰 발급 API")
    @PostMapping("/token")
    public ResponseEntity<AuthTokenPostResDTO> postToken(@Valid @RequestBody AuthTokenPostReqDTO reqVo) {
        return ResponseEntity.ok(QueueTokenMapper.toDtoFromPost(authService.saveToken(QueueTokenMapper.toEntity(reqVo))));
    }

    // 유저 대기열 토큰 조회 API
    @Operation(summary = "유저 대기열 토큰 조회 API")
    @GetMapping("/token/{uuid}")
    public ResponseEntity<AuthTokenGetResDTO> getToken(@PathVariable String uuid) {
        return ResponseEntity.ok(QueueTokenMapper.toDtoFromGet(authService.getToken(uuid)));
    }
}
