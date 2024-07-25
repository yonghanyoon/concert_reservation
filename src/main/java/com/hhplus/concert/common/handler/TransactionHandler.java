package com.hhplus.concert.common.handler;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionHandler {

    @Transactional
    public void runWithTransaction(Consumer consumer) {
        consumer.accept(consumer);
    }
}
