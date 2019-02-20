package com.agendamento.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamento.domain.Cidade;
import com.agendamento.domain.Endereco;
import com.agendamento.domain.Funcionario;
import com.agendamento.domain.enums.Perfil;
import com.agendamento.dto.FuncionarioDTO;
import com.agendamento.dto.FuncionarioNewDTO;
import com.agendamento.repositories.EnderecoRepository;
import com.agendamento.repositories.FuncionarioRepository;
import com.agendamento.security.UserSS;
import com.agendamento.services.exceptions.AuthorizationException;
import com.agendamento.services.exceptions.DataIntegrityException;
import com.agendamento.services.exceptions.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPE;

	public Funcionario find(Integer id) {

		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Funcionario> obj = funcionarioRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Funcionario.class.getName()));
	}

	@Transactional
	public Funcionario insert(Funcionario obj) {
		obj.setId(null);
		obj = funcionarioRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Funcionario update(Funcionario obj) {
		Funcionario newObj = find(obj.getId());
		updateData(newObj, obj);
		return funcionarioRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			funcionarioRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}

	}

	public List<Funcionario> findAll() {
		return funcionarioRepository.findAll();
	}

	public Funcionario findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Funcionario obj = funcionarioRepository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Funcionario.class.getName());
		}
		return obj;
	}

	public Page<Funcionario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return funcionarioRepository.findAll(pageRequest);
	}

	public Funcionario fromDTO(FuncionarioDTO objDto) {
		return new Funcionario(null, objDto.getCpf(), objDto.getNome(), objDto.getEmail(), objDto.getSenha());
	}

	public Funcionario fromDTO(FuncionarioNewDTO objDto) {
		Funcionario cli = new Funcionario(null, objDto.getCpf(), objDto.getNome(), objDto.getEmail(),
				bCryptPE.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null)
			cli.getTelefones().add(objDto.getTelefone2());
		if (objDto.getTelefone3() != null)
			cli.getTelefones().add(objDto.getTelefone3());
		return cli;
	}

	private void updateData(Funcionario newObj, Funcionario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
