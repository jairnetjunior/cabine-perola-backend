package com.example.cabineperola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cabineperola.domain.AgendamentoProcedimento;

@Repository
public interface AgendamentoProcedimentoRepository extends JpaRepository<AgendamentoProcedimento, Integer>{
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM AgendamentoProcedimento obj WHERE obj.id.agendamento.id = :agendamentoId"
			+ " AND obj.id.procedimento.id = :procedimentoId")
	AgendamentoProcedimento findById(@Param("agendamentoId") Integer agendamentoId,
			@Param("procedimentoId") Integer procedimentoId);

}
