package com.agendamento.dto;

import java.io.Serializable;

import javax.validation.constraints.Digits;

import com.agendamento.domain.AgendamentoProcedimento;

public class AgendamentoProcedimentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer quantidade;

	@Digits(integer = 6, fraction = 2, message = "Formato inválido")
	private double preco;

	private Integer procedimentoId;

	private Integer agendamentoId;

	public AgendamentoProcedimentoDTO() {
	}

	public AgendamentoProcedimentoDTO(AgendamentoProcedimento obj) {
		this.quantidade = obj.getQuantidade();
		this.preco = obj.getPreco();
		this.agendamentoId = obj.getId().getAgendamento().getId();
		this.procedimentoId = obj.getId().getProcedimento().getId();
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Integer getProcedimentoId() {
		return procedimentoId;
	}

	public void setProcedimentoId(Integer procedimentoId) {
		this.procedimentoId = procedimentoId;
	}

	public Integer getAgendamentoId() {
		return agendamentoId;
	}

	public void setAgendamentoId(Integer agendamentoId) {
		this.agendamentoId = agendamentoId;
	}
}
