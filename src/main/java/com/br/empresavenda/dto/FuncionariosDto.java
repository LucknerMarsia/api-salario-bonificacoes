package com.br.empresavenda.dto;

import com.br.empresavenda.entities.Cargos;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class FuncionariosDto {
    private long id;
    private String nome;
    private Cargos cargo;
    private Date contratacao;
}
