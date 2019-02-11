package com.example.cabineperola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cabineperola.domain.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{

	@Transactional(readOnly=true)
	Funcionario findByEmail(String email);
	
	@Transactional(readOnly=true)
	Funcionario findByCpf(String cpf);
	
}
