package com.hhplus.concert.api.reservation.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlackApiClient {

    public void sendMessageToDeveloper(Long reservationId) {
        try {
            log.info(String.format("[Slack] : 슬랙 메세지 전송 성공 => reservationId : %d 메세지 발행 실패", reservationId));
        } catch (Exception e) {
            log.error("[Slack] : 슬랙 메세지 전송 실패 => " + e.getMessage());
        }
    }
}
