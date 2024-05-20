package com.br.empresavenda.service;

import com.br.empresavenda.dto.FuncionarioSalarioDto;
import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.entities.Cargos;
import com.br.empresavenda.mapper.FuncionariosResponseMapper;
import com.br.empresavenda.repository.FuncionariosRepository;
import com.br.empresavenda.utils.ConverterData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionariosService {
    private final FuncionariosRepository repository;

    public List<FuncionarioSalarioDto> listar(){
        var funcionariosLista =
                repository
                        .findAll()
                        .stream()
                        .map(FuncionariosResponseMapper::fromEntityToResponse)
                        .toList();

        List<FuncionarioSalarioDto> listaFuncionariosSalario = new ArrayList<>();

        for (FuncionariosDto funcionarioDto : funcionariosLista) {
            var totalSalario =
                    getBeneficiosPorcentage(funcionarioDto.getCargo());


            var funcionarioSalarioDto = FuncionarioSalarioDto.builder()
                    .mesAno(ConverterData.converterDataParaMesAno(funcionarioDto.getContratacao()))
                    .nome(funcionarioDto.getNome())
                    .salario(totalSalario)
                    .build();

            listaFuncionariosSalario.add(funcionarioSalarioDto);
        }
        return listaFuncionariosSalario;
    }

    private BigDecimal getBeneficiosPorcentage(Cargos cargo) {
        if (cargo.getBeneficios() != 0.0) {
            var salario = cargo.getSalario() + cargo.getBonusano();
            var percentual = cargo.getBeneficios() / 100.0;
            var totalSalario =  salario + (percentual * salario);
            return BigDecimal.valueOf(totalSalario).setScale(2, BigDecimal.ROUND_HALF_UP);//return (cargo.getSalario() + cargo.getBonusano()) /(cargo.getBeneficios() * 100);
        }else{
            if(cargo.getFuncao().equals("Gerente")){
                var salario = cargo.getSalario() + cargo.getBonusano();
                return BigDecimal.valueOf(salario).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return BigDecimal.valueOf(0.00);
    }
}