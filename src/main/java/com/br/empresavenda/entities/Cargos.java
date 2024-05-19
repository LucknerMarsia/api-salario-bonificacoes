package com.br.empresavenda.entities;

import jakarta.persistence.*;
import lombok.Data;

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
    private int beneficios;


}
