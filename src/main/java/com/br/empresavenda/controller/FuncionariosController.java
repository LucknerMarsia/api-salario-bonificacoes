package com.br.empresavenda.controller;


import com.br.empresavenda.dto.FuncionarioSalarioDto;
import com.br.empresavenda.dto.FuncionariosDto;
import com.br.empresavenda.repository.FuncionariosRepository;
import com.br.empresavenda.service.FuncionariosService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping("/funcionarios")
@ResponseBody
public class FuncionariosController {


    private final FuncionariosService service;
    private final FuncionariosService funcionarioService;

    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Lista os funcionários")
    @GetMapping(value = "",produces = APPLICATION_JSON_VALUE)
    public List<FuncionarioSalarioDto> lista(){
        return service.listar();
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Salários no mês")
    @GetMapping("/listarsalariomes")
    public List<FuncionarioSalarioDto> listaSalarioMes(){
        return service.listaSalarioMes();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Lista os beneficios")
    @GetMapping(value = "/beneficios",produces = APPLICATION_JSON_VALUE)
    public List<FuncionarioSalarioDto> listBeneficios() {
        return funcionarioService.calcularTotalBeneficios();
    }


}
