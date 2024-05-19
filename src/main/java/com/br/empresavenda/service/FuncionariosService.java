package com.br.empresavenda.service;


import com.br.empresavenda.dto.FuncionarioSalarioDto;
import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.mapper.FuncionariosResponseMapper;
import com.br.empresavenda.repository.FuncionariosRepository;
import com.br.empresavenda.utils.ConverterData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionariosService {


    private final FuncionariosRepository repository;

    private final List<FuncionarioSalarioDto> listaFuncionariosSalario;

    private final FuncionarioSalarioDto funcionarioSalarioDto;


    public List<FuncionarioSalarioDto> listar(){
        var funcionariosLista =
                repository
                        .findAll()
                        .stream()
                        .map(FuncionariosResponseMapper::fromEntityToResponse)
                        .toList();

        // Use maps to collect the data
        Map<YearMonth, Map<String, Double>> summaryMap = new HashMap<>();

        for (FuncionariosDto funcionarioDto : funcionariosLista) {
            funcionarioSalarioDto.setMesAno(ConverterData.converterDataParaMesAno(funcionarioDto.getContratacao()));
            funcionarioSalarioDto.setNome(funcionarioDto.getNome());
            funcionarioSalarioDto.setSalario(funcionarioDto.getCargo().getSalario());

            listaFuncionariosSalario.add(funcionarioSalarioDto);
        }


        return listaFuncionariosSalario;
    }
}
