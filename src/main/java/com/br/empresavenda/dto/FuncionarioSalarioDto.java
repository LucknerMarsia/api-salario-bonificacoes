package com.br.empresavenda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioSalarioDto {

    private YearMonth mesAno;
    private String nome;
    private double salario;
}
