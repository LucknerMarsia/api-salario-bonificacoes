package com.br.empresavenda.dto;

import com.br.empresavenda.entities.Cargos;
import com.br.empresavenda.entities.Vendas;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class FuncionariosDto {
    private long id;
    private String nome;
    private Cargos cargo;
    private Date contratacao;
    private List<Vendas> vendas;
}
