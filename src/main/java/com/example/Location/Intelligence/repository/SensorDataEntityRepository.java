package com.example.Location.Intelligence.repository;

import com.example.Location.Intelligence.model.SensorDataEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SensorDataEntityRepository extends JpaRepository<SensorDataEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SensorDataEntity d WHERE d.createdAt < :cutoff")
    void deleteOlderThan(@Param("cutoff") LocalDateTime cutoff);
}
