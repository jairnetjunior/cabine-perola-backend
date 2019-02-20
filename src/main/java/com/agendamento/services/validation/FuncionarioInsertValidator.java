package com.agendamento.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.agendamento.domain.Funcionario;
import com.agendamento.dto.FuncionarioNewDTO;
import com.agendamento.repositories.FuncionarioRepository;
import com.agendamento.resources.exceptions.FieldMessage;
import com.agendamento.services.validation.utils.BR;

public class FuncionarioInsertValidator implements ConstraintValidator<FuncionarioInsert, FuncionarioNewDTO> {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public void initialize(FuncionarioInsert ann) {
	}

	@Override
	public boolean isValid(FuncionarioNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if (!BR.isValidCPF(objDto.getCpf()))
			list.add(new FieldMessage("cpf", "CPF é inválido"));

		Funcionario aux = funcionarioRepository.findByCpf(objDto.getCpf());
		if (aux != null)
			list.add(new FieldMessage("cpf", "CPF já existente"));

		aux = funcionarioRepository.findByEmail(objDto.getEmail());
		if (aux != null)
			list.add(new FieldMessage("email", "Email já existente"));

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
