package org.antonio.test.springboot.app.repositories;

import java.util.List;
import java.util.Optional;

import org.antonio.test.springboot.app.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("select c from Cuenta c where c.persona=?1")
    Optional<Cuenta> findByPersona(String persona);

    // List<Cuenta> findAll();

    // Cuenta findById(Long id);

    // void update(Cuenta cuenta);
}
