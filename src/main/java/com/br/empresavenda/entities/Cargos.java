package com.br.empresavenda.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "cargos")
public class Cargos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String funcao;
    @Column
    private double salario;
    @Column
    private double bonusano;
    @Column
    @Nullable
    private double beneficios;
    @OneToMany(mappedBy = "cargo")
    @JsonIgnore
    private List<Funcionarios> funcionarios;


}
