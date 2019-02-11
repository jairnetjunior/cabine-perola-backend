package com.example.cabineperola.dto;

import java.io.Serializable;
import java.util.Date;

public class AgendamentoNewDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date data;
	
	private Integer clienteId;
	
	private Integer procedimentoId;
	
	private Integer quantidade;

	
	
	public AgendamentoNewDTO() {
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

	public Integer getProcedimentoId() {
		return procedimentoId;
	}

	public void setProcedimentoId(Integer procedimentoId) {
		this.procedimentoId = procedimentoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
}
