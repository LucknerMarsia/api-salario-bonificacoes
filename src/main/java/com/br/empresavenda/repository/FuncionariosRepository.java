package com.br.empresavenda.repository;

import com.br.empresavenda.entities.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionarios, Long> {

    List<Funcionarios> findByNome(String nome);
}
