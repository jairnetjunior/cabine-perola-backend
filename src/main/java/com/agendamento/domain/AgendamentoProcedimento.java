package com.agendamento.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class AgendamentoProcedimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AgendamentoProcedimentoPK id = new AgendamentoProcedimentoPK();

	private Integer quantidade;
	private double preco;

	public AgendamentoProcedimento() {

	}

	public AgendamentoProcedimento(Agendamento agendamento, Procedimento procedimento, Integer quantidade,
			double preco) {
		super();
		this.id.setAgendamento(agendamento);
		this.id.setProcedimento(procedimento);
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public double getSubTotal() {
		return preco * quantidade;
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

	public Procedimento getProcedimento() {
		return this.id.getProcedimento();
	}

	public AgendamentoProcedimentoPK getId() {
		return id;
	}

	public void setId(AgendamentoProcedimentoPK id) {
		this.id = id;
	}
}
