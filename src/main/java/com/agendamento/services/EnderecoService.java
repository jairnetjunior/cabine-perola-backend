package com.agendamento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamento.domain.Cidade;
import com.agendamento.domain.Endereco;
import com.agendamento.domain.Pessoa;
import com.agendamento.dto.EnderecoDTO;
import com.agendamento.repositories.CidadeRepository;
import com.agendamento.repositories.EnderecoRepository;
import com.agendamento.repositories.PessoaRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Transactional
	public Endereco insert(Endereco obj) {
		obj.setId(null);
		obj = enderecoRepository.save(obj);
		return obj;
	}

	public List<Endereco> findByPessoa(Integer pessoaId) {
		return enderecoRepository.findByPessoaId(pessoaId);
	}

	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}

	public Endereco fromDTO(EnderecoDTO objDto) {
		Pessoa pessoa = pessoaRepository.findById(objDto.getPessoaId()).get();
		Cidade cidade = cidadeRepository.findById(objDto.getCidadeId()).get();
		return new Endereco(objDto.getId(), objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), pessoa, cidade);
	}

}
