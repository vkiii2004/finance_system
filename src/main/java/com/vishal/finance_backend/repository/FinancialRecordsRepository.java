package com.vishal.finance_backend.repository;

import com.vishal.finance_backend.Entity.FinancialRecords;
import com.vishal.finance_backend.enums.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FinancialRecordsRepository extends JpaRepository<FinancialRecords,Long> {
    List<FinancialRecords>findByUserId(Long userId);

    @Query("""
            SELECT fr
            FROM FinancialRecords fr
            WHERE fr.user.id = :userId
              AND (:type IS NULL OR fr.type = :type)
              AND (:category IS NULL OR fr.category = :category)
              AND (:startDate IS NULL OR fr.date >= :startDate)
              AND (:endDate IS NULL OR fr.date <= :endDate)
            """)
    Page<FinancialRecords> search(
            @Param("userId") Long userId,
            @Param("type") RecordType type,
            @Param("category") String category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    Page<FinancialRecords> findByUserId(Long userId, Pageable pageable);

    Optional<FinancialRecords> findByIdAndUser_Id(Long id, Long userId);

    List<FinancialRecords> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
