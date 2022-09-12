package org.antonio.test.springboot.app.repositories;

import java.util.List;

import org.antonio.test.springboot.app.models.Banco;

public interface BancoRepository {
    List<Banco> findAll();

    Banco findById(Long id);

    void update(Banco banco);
}
