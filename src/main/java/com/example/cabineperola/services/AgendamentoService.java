package com.example.cabineperola.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cabineperola.domain.Agendamento;
import com.example.cabineperola.domain.AgendamentoProcedimento;
import com.example.cabineperola.domain.Cliente;
import com.example.cabineperola.domain.Procedimento;
import com.example.cabineperola.dto.AgendamentoDTO;
import com.example.cabineperola.dto.AgendamentoNewDTO;
import com.example.cabineperola.repositories.AgendamentoRepository;
import com.example.cabineperola.repositories.ClienteRepository;
import com.example.cabineperola.services.exceptions.DataIntegrityException;
import com.example.cabineperola.services.exceptions.ObjectNotFoundException;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	public Agendamento find(Integer id) {
		Optional<Agendamento> obj = agendamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Agendamento.class.getName()));
	}

	@Transactional

	public Agendamento insert(Agendamento obj) {

		obj.setId(null);
		obj = agendamentoRepository.save(obj);

		return obj;

	}

	public Agendamento update(Agendamento obj) {
		Agendamento newObj = find(obj.getId());
		updateDate(newObj, obj);
		return agendamentoRepository.save(newObj);
	}

	public void delete(Integer id) {
		Agendamento agendamento = find(id);
		if(agendamento.getAgendamentos()==null) {
			try {
				agendamentoRepository.delete(agendamento);
			} catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
			}
		}
		else
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
	}

	public List<Agendamento> findAll() {
		return agendamentoRepository.findAll();
	}

	public Page<Agendamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return agendamentoRepository.findAll(pageRequest);
	}

	public Agendamento fromDTO(AgendamentoDTO objDto) {
		return new Agendamento(null, objDto.getData(), new Cliente(objDto.getClienteId(), null, null, null, null));
	}

	public Agendamento fromDTO(AgendamentoNewDTO objDto) {
		Cliente cliente = clienteRepository.findById(objDto.getClienteId()).get();
		Agendamento agendamento = new Agendamento(null, objDto.getData(), cliente);
		Procedimento procedimento = new Procedimento(objDto.getProcedimentoId(), null, null, 0.00);
		AgendamentoProcedimento agendamentoProcedimento = new AgendamentoProcedimento(agendamento, procedimento, objDto.getQuantidade(), procedimento.getPreco());
		Set<AgendamentoProcedimento> agendamentos = new HashSet<>();
		agendamentos.add(agendamentoProcedimento);
		agendamento.setAgendamentos(agendamentos);
		return agendamento;
	}

	private void updateDate(Agendamento newObj, Agendamento obj) {
		newObj.setData(obj.getData());
	}

}
