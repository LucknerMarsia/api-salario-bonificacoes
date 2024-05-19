package com.br.empresavenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EmpresaVendaApplication {

	/*public static void main(String[] args)
	{
		SpringApplication.run(EmpresaVendaApplication.class, args);
	}*/

	public static void main(String[] args) {
		new SpringApplicationBuilder(EmpresaVendaApplication.class
		).run(args);
	}

}
