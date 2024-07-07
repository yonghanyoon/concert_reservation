package com.hhplus.concert.api.payment.presentation;

import com.hhplus.concert.api.payment.presentation.dto.request.PaymentReqVo;
import com.hhplus.concert.api.payment.presentation.dto.response.PaymentResVo;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    // 결제 API
    @PostMapping("")
    public ResponseEntity<PaymentResVo> postPayment(HttpServletRequest request, PaymentReqVo reqVo) {
        return ResponseEntity.ok(new PaymentResVo(1L, LocalDateTime.now()));
    }
}
