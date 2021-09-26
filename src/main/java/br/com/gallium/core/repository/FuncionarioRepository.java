package br.com.gallium.core.repository;

import br.com.gallium.core.model.Cargo;
import br.com.gallium.core.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeFuncionarioContainsIgnoreCase(String nome);
    List<Funcionario> findByNomeFuncionarioContainsIgnoreCaseAndCargo(String nome, Cargo cargo);
    List<Funcionario> findByCpfAfter(long cpf);
}