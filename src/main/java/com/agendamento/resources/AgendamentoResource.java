
package com.agendamento.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agendamento.domain.Agendamento;
import com.agendamento.dto.AgendamentoDTO;
import com.agendamento.dto.AgendamentoNewDTO;
import com.agendamento.services.AgendamentoService;

@RestController

@RequestMapping(value = "/agendamentos")
public class AgendamentoResource {

	@Autowired
	private AgendamentoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Agendamento> find(@PathVariable Integer id) {
		Agendamento obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/clienteId", method = RequestMethod.GET)
	public ResponseEntity<List<AgendamentoDTO>> findByCliente(
			@RequestParam(value = "value", defaultValue = "0") Integer clienteId) {
		List<Agendamento> list = service.findByCliente(clienteId);
		List<AgendamentoDTO> listDTO = list.stream().map(obj -> new AgendamentoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AgendamentoNewDTO objDto) {
		Agendamento obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AgendamentoDTO objDto, @PathVariable Integer id) {
		Agendamento obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AgendamentoDTO>> findAll() {
		List<Agendamento> list = service.findAll();
		List<AgendamentoDTO> listDTO = list.stream().map(obj -> new AgendamentoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<AgendamentoDTO>> findPage(

			@RequestParam(value = "page", defaultValue = "0") Integer page,

			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,

			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,

			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Agendamento> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AgendamentoDTO> listDTO = list.map(obj -> new AgendamentoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

}
