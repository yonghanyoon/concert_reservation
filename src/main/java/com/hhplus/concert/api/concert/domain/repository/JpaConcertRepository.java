package com.hhplus.concert.api.concert.domain.repository;

import com.hhplus.concert.api.concert.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaConcertRepository extends JpaRepository<Concert, Long> {

}
