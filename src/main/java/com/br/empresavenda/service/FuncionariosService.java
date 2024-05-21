package com.br.empresavenda.service;

import com.br.empresavenda.dto.FuncionarioSalarioDto;
import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.mapper.FuncionariosResponseMapper;
import com.br.empresavenda.repository.FuncionariosRepository;
import com.br.empresavenda.utils.ConverterData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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


        for (FuncionariosDto funcionariosDto : funcionariosLista) {
            var totalSalario = getBeneficiosPorcentage(funcionariosDto);
                    getBeneficiosPorcentage(funcionariosDto);


            var funcionarioSalarioDto = FuncionarioSalarioDto.builder()
                    .mesAno(ConverterData.converterDataParaMesAno(funcionariosDto.getContratacao()))
                    .nome(funcionariosDto.getNome())
                    .salario(totalSalario)
                    .build();

            listaFuncionariosSalario.add(funcionarioSalarioDto);
        }
        return listaFuncionariosSalario;
    }

    private BigDecimal getBeneficiosPorcentage(FuncionariosDto funcionariosDto) {

        if (funcionariosDto.getCargo().getBeneficios() != 0.0) {

            LocalDate dataContratacao = funcionariosDto.getContratacao().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            var bonus = periodoEntreDatas(dataContratacao) * funcionariosDto.getCargo().getBonusano();
            var salario = funcionariosDto.getCargo().getSalario() + bonus;
            var percentual = funcionariosDto.getCargo().getBeneficios() / 100.0;
            var totalSalario =  salario + (percentual * salario);
            return BigDecimal.valueOf(totalSalario).setScale(2, BigDecimal.ROUND_HALF_UP);//return (cargo.getSalario() + cargo.getBonusano()) /(cargo.getBeneficios() * 100);
        }else{
            if(funcionariosDto.getCargo().getFuncao().equals("Gerente")){
                var salario = funcionariosDto.getCargo().getSalario() + funcionariosDto.getCargo().getBonusano();
                return BigDecimal.valueOf(salario).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return BigDecimal.valueOf(0.00);
    }

    public List<FuncionarioSalarioDto> listaSalarioMes() {
        return repository.findAll().stream()
                .map(FuncionariosResponseMapper::fromEntityToResponse)
                .map(funcionarioDto -> new FuncionarioSalarioDto(
                        ConverterData.converterDataParaMesAno(funcionarioDto.getContratacao()),
                        "Total Salário",
                        BigDecimal.valueOf(funcionarioDto.getCargo().getSalario())
                ))
                .collect(Collectors.groupingBy(FuncionarioSalarioDto::getMesAno,
                        TreeMap::new, Collectors.reducing(BigDecimal.ZERO,
                                FuncionarioSalarioDto::getSalario,
                                BigDecimal::add)))
                .entrySet().stream()
                .map(entry -> new FuncionarioSalarioDto(entry.getKey(), "Total Salário", entry.getValue()))
                .collect(Collectors.toList());
    }
   public static int periodoEntreDatas(LocalDate dataInicial) {
        LocalDate dataFinal = LocalDate.now();
        var periodo = Period.between(dataInicial, dataFinal);

        return periodo.getYears();
    }
}