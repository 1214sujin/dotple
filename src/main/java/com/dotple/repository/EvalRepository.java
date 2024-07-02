package com.dotple.repository;

import com.dotple.domain.Eval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvalRepository extends JpaRepository<Eval, Long> {
}
