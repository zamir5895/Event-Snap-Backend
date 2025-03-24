package com.backend.users.Confirmation.Infrastructure;

import com.backend.users.Confirmation.Domain.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConfirmationRepository extends JpaRepository<Confirmation,Long> {

    @Query("SELECT c FROM Confirmation c WHERE c.userId=:userId")
    Optional<Confirmation> findByUserId(@Param("userId") Long userId);

    @Query("DELETE  FROM Confirmation c WHERE c.userId = :userId")
    Integer deleteByUserId(@Param("userId") Long userId);

    @Query("UPDATE Confirmation  c SET c.code = :newCode WHERE c.userId = :userId")
    Integer updateCode(@Param("userId") Long userId, @Param("newCode") String newCode);
}
