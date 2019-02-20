package com.agendamento.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamento.domain.Funcionario;
import com.agendamento.domain.Procedimento;
import com.agendamento.dto.ProcedimentoDTO;
import com.agendamento.dto.ProcedimentoNewDTO;
import com.agendamento.repositories.FuncionarioRepository;
import com.agendamento.repositories.ProcedimentoRepository;
import com.agendamento.services.exceptions.DataIntegrityException;
import com.agendamento.services.exceptions.ObjectNotFoundException;

@Service
public class ProcedimentoService {

	@Autowired
	private ProcedimentoRepository procedimentoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public Procedimento find(Integer id) {
		Optional<Procedimento> obj = procedimentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Procedimento.class.getName()));
	}

	@Transactional

	public Procedimento insert(Procedimento obj) {
		obj.setId(null);
		obj = procedimentoRepository.save(obj);
		funcionarioRepository.save(obj.getFuncionario());
		return obj;
	}

	public Procedimento update(Procedimento obj) {
		Procedimento newObj = find(obj.getId());
		updateData(newObj, obj);
		return procedimentoRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}

	}

	public List<Procedimento> findAll() {
		return procedimentoRepository.findAll();
	}

	public Page<Procedimento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return procedimentoRepository.findAll(pageRequest);
	}

	public Procedimento fromDTO(ProcedimentoDTO objDto) {
		return new Procedimento(null, objDto.getNome(), objDto.getDescricao(), objDto.getPreco());
	}

	public Procedimento fromDTO(ProcedimentoNewDTO objDto) {
		Procedimento procedimento = new Procedimento(null, objDto.getNome(), objDto.getDescricao(), objDto.getPreco());
		Funcionario funcionario = funcionarioRepository.findById(objDto.getFuncionarioId()).get();
		funcionario.getProcedimentos().add(procedimento);
		procedimento.setFuncionario(funcionario);

		return procedimento;
	}

	private void updateData(Procedimento newObj, Procedimento obj) {
		newObj.setNome(obj.getNome());
		newObj.setDescricao(obj.getDescricao());
		newObj.setPreco(obj.getPreco());
	}
}
