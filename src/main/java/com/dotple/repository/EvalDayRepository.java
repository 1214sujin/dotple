package com.dotple.repository;

import com.dotple.domain.EvalDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvalDayRepository extends JpaRepository<EvalDay, Long> {
}
