package com.agendamento.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.agendamento.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Funcionario extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
	private List<Procedimento> procedimentos = new ArrayList<>();

	public Funcionario() {
		addPerfil(Perfil.FUNCIONARIO);
	}

	public Funcionario(Integer id, String cpf, String nome, String email, String senha) {
		super(id, cpf, nome, email, senha);
	}

	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

}
