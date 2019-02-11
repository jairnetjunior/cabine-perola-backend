package com.example.cabineperola.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cabineperola.domain.AgendamentoProcedimento;
import com.example.cabineperola.dto.AgendamentoProcedimentoDTO;
import com.example.cabineperola.dto.AgendamentoProcedimentoNewDTO;
import com.example.cabineperola.services.AgendamentoProcedimentoService;

@RestController
@RequestMapping(value="/sessoes")
public class AgendamentoProcedimentoResource {
	
	@Autowired
	private AgendamentoProcedimentoService service;
	
	@RequestMapping(value="/GET", method=RequestMethod.GET)
	public ResponseEntity<AgendamentoProcedimentoDTO> 
					find(@RequestParam(value="agendamentoId", defaultValue="0") Integer agendamentoId,
						 @RequestParam(value="procedimentoId", defaultValue="0") Integer procedimentoId) {
		AgendamentoProcedimento obj = service.find(agendamentoId, procedimentoId);
		return ResponseEntity.ok().body(new AgendamentoProcedimentoDTO(obj));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<AgendamentoProcedimento> insert(@Valid @RequestBody AgendamentoProcedimentoNewDTO objDto){
		AgendamentoProcedimento obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/PUT", method = RequestMethod.PUT)
	public ResponseEntity<Void> 
					update(@Valid @RequestBody AgendamentoProcedimentoDTO objDto,
						   @RequestParam(value="agendamentoId", defaultValue="0") Integer agendamentoId,
						   @RequestParam(value="procedimentoId", defaultValue="0") Integer procedimentoId){
		AgendamentoProcedimento obj = service.fromDTO(objDto);
		obj.getId().getAgendamento().setId(agendamentoId);
		obj.getId().getProcedimento().setId(procedimentoId);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/DELETE", method=RequestMethod.DELETE)
	public ResponseEntity<Void> 
					delete(@RequestParam(value="agendamentoId", defaultValue="0") Integer agendamentoId,
						   @RequestParam(value="procedimentoId", defaultValue="0") Integer procedimentoId) {
		service.delete(agendamentoId, procedimentoId);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AgendamentoProcedimentoDTO>> findAll() {
		List<AgendamentoProcedimento> list = service.findAll();
		List<AgendamentoProcedimentoDTO> listDTO = list.stream().map(obj -> new AgendamentoProcedimentoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<AgendamentoProcedimentoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<AgendamentoProcedimento> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AgendamentoProcedimentoDTO> listDTO = list.map(obj -> new AgendamentoProcedimentoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
