package com.finpulse.insight_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "insights",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_month", columnNames = {"user_id", "month", "year"})
        },
        indexes = {
                @Index(name = "ix_insight_user_id", columnList = "user_id"),
                @Index(name = "ix_insight_month_year", columnList = "month, year")
        }
)
public class Insight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insight_id")
    private Long insightId;

    @Column(name = "user_id", nullable = false)
    private Long userId;  // reference to auth_db

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "total_income", precision = 15, scale = 2)
    private BigDecimal totalIncome = BigDecimal.ZERO;

    @Column(name = "total_expense", precision = 15, scale = 2)
    private BigDecimal totalExpense = BigDecimal.ZERO;

    @Transient
    public BigDecimal getSavings() {
        if (totalIncome == null || totalExpense == null) return BigDecimal.ZERO;
        return totalIncome.subtract(totalExpense);
    }

    @Column(name = "top_spending_category", length = 100)
    private String topSpendingCategory;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;  // From AI service

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
