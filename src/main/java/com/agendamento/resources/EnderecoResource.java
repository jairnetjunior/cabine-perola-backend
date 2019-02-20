package com.agendamento.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agendamento.domain.Endereco;
import com.agendamento.dto.EnderecoDTO;
import com.agendamento.services.EnderecoService;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {

	@Autowired
	private EnderecoService enderecoService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EnderecoDTO>> findAll() {
		List<Endereco> list = enderecoService.findAll();
		List<EnderecoDTO> listDTO = list.stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody EnderecoDTO objDto) {
		Endereco obj = enderecoService.fromDTO(objDto);
		obj = enderecoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/pessoaId", method = RequestMethod.GET)
	public ResponseEntity<List<EnderecoDTO>> findByCliente(@RequestParam(value = "value") Integer pessoaId) {
		List<Endereco> list = enderecoService.findByPessoa(pessoaId);
		List<EnderecoDTO> listDTO = list.stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

}
