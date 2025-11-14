package com.finpulse.insight_service.repo;

import com.finpulse.insight_service.model.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long> {
    Optional<Insight> findByUserIdAndMonthAndYear(Long userId, Integer month, Integer year);
    List<Insight> findByUserIdOrderByYearDescMonthDesc(Long userId);
}

