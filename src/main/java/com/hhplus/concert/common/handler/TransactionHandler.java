package com.hhplus.concert.common.handler;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionHandler {

    @Transactional
    public <T> void runWithTransaction(Consumer<T> consumer, T argument) {
        consumer.accept(argument);
    }

    @Transactional
    public <T, U> void runWithTransaction(BiConsumer<T, U> consumer, T argument1, U argument2) {
        consumer.accept(argument1, argument2);
    }
}
