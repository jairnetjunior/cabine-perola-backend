package com.example.cabineperola.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cabineperola.domain.Agendamento;
import com.example.cabineperola.domain.AgendamentoProcedimento;
import com.example.cabineperola.domain.Procedimento;
import com.example.cabineperola.dto.AgendamentoProcedimentoDTO;
import com.example.cabineperola.dto.AgendamentoProcedimentoNewDTO;
import com.example.cabineperola.repositories.AgendamentoProcedimentoRepository;
import com.example.cabineperola.services.exceptions.DataIntegrityException;
import com.example.cabineperola.services.exceptions.ObjectNotFoundException;

@Service
public class AgendamentoProcedimentoService {

	@Autowired
	private AgendamentoProcedimentoRepository agendamentoProcedimentoRepository;

	public AgendamentoProcedimento find(Integer agendamentoId, Integer procedimentoId) {
			AgendamentoProcedimento obj = agendamentoProcedimentoRepository.findById(agendamentoId, procedimentoId);
			if(obj == null) {
				throw new ObjectNotFoundException(
						"Objeto não encontrado! Tipo: " + AgendamentoProcedimento.class.getName());
			}
			else
				return obj;
	}

	@Transactional

	public AgendamentoProcedimento insert(AgendamentoProcedimento obj) {

		obj.setId(null);
		obj = agendamentoProcedimentoRepository.save(obj);

		return obj;

	}

	public AgendamentoProcedimento update(AgendamentoProcedimento obj) {
		AgendamentoProcedimento newObj = 
				find(obj.getId().getAgendamento().getId(), obj.getId().getProcedimento().getId());
		updateData(newObj, obj);
		return agendamentoProcedimentoRepository.save(newObj);
	}

	public void delete(Integer agendamentoId, Integer procedimentoId) {
		AgendamentoProcedimento obj = find(agendamentoId, procedimentoId);
		try {
			agendamentoProcedimentoRepository.delete(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<AgendamentoProcedimento> findAll() {
		return agendamentoProcedimentoRepository.findAll();
	}

	public Page<AgendamentoProcedimento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return agendamentoProcedimentoRepository.findAll(pageRequest);
	}

	public AgendamentoProcedimento fromDTO(AgendamentoProcedimentoDTO objDto) {
		return new AgendamentoProcedimento(new Agendamento(), new Procedimento(), 
				objDto.getQuantidade(), objDto.getPreco() );
	}

	public AgendamentoProcedimento fromDTO(AgendamentoProcedimentoNewDTO objDto) {
		AgendamentoProcedimento agendamentoProcedimento = 
				new AgendamentoProcedimento(objDto.getId().getAgendamento(),
						objDto.getId().getProcedimento(), objDto.getQuantidade(), objDto.getPreco());
		return agendamentoProcedimento;
	}
	
	private void updateData(AgendamentoProcedimento newObj, AgendamentoProcedimento obj) {
		newObj.setQuantidade(obj.getQuantidade());
		newObj.setPreco(obj.getPreco());
		newObj.setId(obj.getId());
	}
	
}
