package com.dotple.repository;

import com.dotple.domain.Belong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BelongRepository extends JpaRepository<Belong, Long> {
}
