package com.hhplus.concert.api.token.presentation;

import com.hhplus.concert.api.token.application.TokenService;
import com.hhplus.concert.api.token.presentation.dto.request.TokenPostReqDTO;
import com.hhplus.concert.api.token.presentation.dto.response.TokenPostResDTO;
import com.hhplus.concert.api.token.presentation.dto.response.TokenGetResDTO;
import com.hhplus.concert.api.token.presentation.mapper.TokenMapper;
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
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // 유저 대기열 토큰 발급 API
    @Operation(summary = "유저 대기열 토큰 발급 API")
    @PostMapping("")
    public ResponseEntity<TokenPostResDTO> postToken(@Valid @RequestBody TokenPostReqDTO reqVo) {
        return ResponseEntity.ok(
            TokenMapper.toDtoFromPost(tokenService.saveToken(TokenMapper.toEntity(reqVo))));
    }

    // 유저 대기열 토큰 조회 API
    @Operation(summary = "유저 대기열 토큰 조회 API")
    @GetMapping("/{uuid}")
    public ResponseEntity<TokenGetResDTO> getToken(@PathVariable String uuid) {
        return ResponseEntity.ok(TokenMapper.toDtoFromGet(tokenService.getToken(uuid)));
    }
}
