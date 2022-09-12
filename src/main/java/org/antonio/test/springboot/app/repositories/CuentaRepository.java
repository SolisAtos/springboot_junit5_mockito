package org.antonio.test.springboot.app.repositories;

import java.util.List;

import org.antonio.test.springboot.app.models.Cuenta;

public interface CuentaRepository {
    List<Cuenta> findAll();

    Cuenta findById(Long id);

    void update(Cuenta cuenta);
}
