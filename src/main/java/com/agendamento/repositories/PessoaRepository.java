package com.agendamento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamento.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
