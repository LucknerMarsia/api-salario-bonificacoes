package com.br.empresavenda.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "Vendas")
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Funcionarios vendedor;
    private Date data;
    private double valor;
}
