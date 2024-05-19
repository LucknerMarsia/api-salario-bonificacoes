package com.br.empresavenda.mapper;

import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.entities.Funcionarios;

public class FuncionariosResponseMapper {

    public static FuncionariosDto fromEntityToResponse(Funcionarios funcionario){
        return FuncionariosDto.builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .cargo(funcionario.getCargo())
                .contratacao(funcionario.getContratacao())
                .build();
    }
}
