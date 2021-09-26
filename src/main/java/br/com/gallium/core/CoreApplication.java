package br.com.gallium.core;

import br.com.gallium.core.model.Cargo;
import br.com.gallium.core.model.Categoria;
import br.com.gallium.core.model.Funcionario;
import br.com.gallium.core.model.LojaDeRoupa;
import br.com.gallium.core.repository.FuncionarioRepository;
import br.com.gallium.core.repository.LojaDeRoupaRepository;
import br.com.gallium.core.service.StorageService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Bean
    public ApplicationRunner firstCarga(LojaDeRoupaRepository lojaDeRoupaRepository,
                                        FuncionarioRepository funcionarioRepository){
       return  args -> {
           LojaDeRoupa loja1 = new LojaDeRoupa("Marisa", 50, "Osasco,SP", 1145678985, Categoria.MATRIZ);
           LojaDeRoupa loja2 = new LojaDeRoupa("Renner", 50, "Osasco,SP", 1145689985, Categoria.MATRIZ);
            lojaDeRoupaRepository.save(loja1);
            lojaDeRoupaRepository.save(loja2);

            Funcionario funcionario1 = new Funcionario("Joaquim", new Long("12345678912"), Cargo.VENDEDOR );
            Funcionario funcionario2 = new Funcionario("Maria", new Long("45678913245"), Cargo.ATENDENTE );
            Funcionario funcionario3 = new Funcionario("Madalena", new Long("78945612389"), Cargo.ESTOQUISTA );
            funcionario1.setLojaDeRoupa(loja1);
            funcionario2.setLojaDeRoupa(loja1);
            funcionario3.setLojaDeRoupa(loja2);

            funcionarioRepository.save(funcionario1);
            funcionarioRepository.save(funcionario2);
            funcionarioRepository.save(funcionario3);
        };
    }


    @Bean
    ApplicationRunner initUploadFolder(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}