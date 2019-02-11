package com.example.cabineperola.dto;

import java.io.Serializable;
import java.util.Date;

import com.example.cabineperola.domain.Agendamento;

public class AgendamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Date data;
	
	private Integer clienteId;
	
	public AgendamentoDTO() {
	}
	
	public AgendamentoDTO(Agendamento obj) {
		id = obj.getId();
		data = obj.getData();
		clienteId = obj.getCliente().getId();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

}
