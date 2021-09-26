package br.com.gallium.core.repository;

import br.com.gallium.core.model.LojaDeRoupa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LojaDeRoupaRepository extends JpaRepository<LojaDeRoupa, Integer> {
    List<LojaDeRoupa> findByNomeLojaContainsIgnoreCase(String nome);
}