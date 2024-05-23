package com.br.empresavenda.service;

import com.br.empresavenda.dto.FuncionarioSalarioDto;
import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.entities.Vendas;
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

        public List<FuncionarioSalarioDto> listar() {
            var funcionariosLista =
                    repository
                            .findAll()
                            .stream()
                            .map(FuncionariosResponseMapper::fromEntityToResponse)
                            .toList();

            List<FuncionarioSalarioDto> listaFuncionariosSalario = new ArrayList<>();


            for (FuncionariosDto funcionariosDto : funcionariosLista) {
                var totalSalario = getBeneficiosPorcentage(funcionariosDto);

                var funcionarioSalarioDto = FuncionarioSalarioDto.builder()
                        .mesAno(ConverterData.converterDataParaMesAno(funcionariosDto.getContratacao()))
                        .nome(funcionariosDto.getNome())
                        .salario(totalSalario)
                        .build();

                listaFuncionariosSalario.add(funcionarioSalarioDto);
            }
            return listaFuncionariosSalario;
        }

        //Primeiro metodo de acordo com as regras
        private BigDecimal getBeneficiosPorcentage(FuncionariosDto funcionario) {

            var salario = 0.00;
            var totalSalario = 0.00;
            var percentual = 0.00;
            var bonus = 0.00;
            var valorVenda = 0.00;

            LocalDate dataContratacao = funcionario.getContratacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            bonus = periodoEntreDatas(dataContratacao) * funcionario.getCargo().getBonusano();

            salario = funcionario.getCargo().getSalario() + bonus;

            if (funcionario.getCargo().getBeneficios() != 0.00) {
                percentual = funcionario.getCargo().getBeneficios() / 100.0;

                if (funcionario.getCargo().getFuncao().equals("Secretario")) {
                    totalSalario = salario + (percentual * salario);

                    return BigDecimal.valueOf(totalSalario);
                } else if (funcionario.getCargo().getFuncao().equals("Vendedor")) {
                    for (Vendas venda : funcionario.getVendas()) {
                        valorVenda = venda.getValor();

                        totalSalario = salario + (percentual * valorVenda);

                        return BigDecimal.valueOf(totalSalario);
                    }
                }
            }
            return BigDecimal.valueOf(salario);

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

        /* para baixo se não der certo é só apagar*/
        /* Este é o terceiro método*/

        public List<FuncionarioSalarioDto> calcularTotalBeneficios() {
            List<FuncionariosDto> funcionarios = repository
                    .findAll()
                    .stream()
                    .filter(f -> f.getCargo().getBeneficios() != 0.0)
                    .map(FuncionariosResponseMapper::fromEntityToResponse)
                    .toList();

            List<FuncionarioSalarioDto> listaFuncionariosSalario = new ArrayList<>();

            for (FuncionariosDto funcionario : funcionarios) {
                if (funcionario.getContratacao() != null) {

                    if (funcionario.getCargo() != null && funcionario.getCargo().getBeneficios() != 0.0) {
                        LocalDate dataContratacao = funcionario.getContratacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        var bonus = periodoEntreDatas(dataContratacao) * funcionario.getCargo().getBonusano();


                        var salario = funcionario.getCargo().getSalario() + bonus;
                        var percentual = funcionario.getCargo().getBeneficios() / 100.0;
                        var totalSalario = salario + (percentual * salario);

                        var totalBeneficio = totalSalario - salario;

                        var funcionarioSalarioDto = FuncionarioSalarioDto.builder()
                                .mesAno(ConverterData.converterDataParaMesAno(funcionario.getContratacao()))
                                .nome(funcionario.getNome())
                                .salario(BigDecimal.valueOf(totalBeneficio))
                                .build();

                        listaFuncionariosSalario.add(funcionarioSalarioDto);
                    }
                }
            }
            return listaFuncionariosSalario;
        }
    }