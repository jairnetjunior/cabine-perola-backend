package com.agendamento.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Agendamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date data;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@JsonIgnore
	@OneToMany(mappedBy = "id.agendamento", cascade = CascadeType.ALL)
	private Set<AgendamentoProcedimento> agendamentos = new HashSet<>();

	public Agendamento() {
	}

	public Agendamento(Integer id, Date data, Cliente cliente) {
		super();
		this.id = id;
		this.data = data;
		this.cliente = cliente;
	}

	public double getValorTotal() {
		double soma = 0;
		for (AgendamentoProcedimento a : agendamentos) {
			soma = soma + a.getSubTotal();
		}
		return soma;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<AgendamentoProcedimento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(Set<AgendamentoProcedimento> agendamentos) {
		this.agendamentos = agendamentos;
	}
}
