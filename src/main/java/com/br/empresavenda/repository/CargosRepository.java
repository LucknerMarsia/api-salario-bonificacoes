package com.br.empresavenda.repository;

import com.br.empresavenda.entities.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargosRepository extends JpaRepository<Cargos, Long> {


}
