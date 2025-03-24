package com.backend.users.Adress.Infrastructure;

import com.backend.users.Adress.Domain.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long> {
}
