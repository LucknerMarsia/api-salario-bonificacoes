package com.br.empresavenda.service;

import com.br.empresavenda.dto.FuncionarioSalarioDto;
import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.mapper.FuncionariosResponseMapper;
import com.br.empresavenda.repository.FuncionariosRepository;
import com.br.empresavenda.utils.ConverterData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<FuncionarioSalarioDto> listaFuncionariosSalario = new ArrayList<>(); // Initialize the list locally

        Map<YearMonth, Map<String, Double>> summaryMap = new HashMap<>();

        for (FuncionariosDto funcionarioDto : funcionariosLista) {
            FuncionarioSalarioDto funcionarioSalarioDto = FuncionarioSalarioDto.builder()
                    .mesAno(ConverterData.converterDataParaMesAno(funcionarioDto.getContratacao()))
                    .nome(funcionarioDto.getNome())
                    .salario(funcionarioDto.getCargo().getSalario())
                    .build();

            listaFuncionariosSalario.add(funcionarioSalarioDto);
        }

        return listaFuncionariosSalario;
    }
}
