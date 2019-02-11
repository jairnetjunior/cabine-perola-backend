package com.example.cabineperola.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cabineperola.domain.Funcionario;
import com.example.cabineperola.dto.FuncionarioNewDTO;
import com.example.cabineperola.repositories.FuncionarioRepository;
import com.example.cabineperola.resources.exceptions.FieldMessage;
import com.example.cabineperola.services.validation.utils.BR;

public class FuncionarioInsertValidator implements ConstraintValidator<FuncionarioInsert, FuncionarioNewDTO> {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public void initialize(FuncionarioInsert ann) {
	}

	@Override
	public boolean isValid(FuncionarioNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if(!BR.isValidCPF(objDto.getCpf()))
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
