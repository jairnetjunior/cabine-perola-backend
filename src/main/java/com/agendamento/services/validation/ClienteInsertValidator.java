package com.agendamento.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.agendamento.domain.Cliente;
import com.agendamento.dto.ClienteNewDTO;
import com.agendamento.repositories.ClienteRepository;
import com.agendamento.resources.exceptions.FieldMessage;
import com.agendamento.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if (!BR.isValidCPF(objDto.getCpf()))
			list.add(new FieldMessage("cpf", "CPF é inválido"));

		Cliente aux = clienteRepository.findByCpf(objDto.getCpf());
		if (aux != null)
			list.add(new FieldMessage("cpf", "CPF já existente"));

		aux = clienteRepository.findByEmail(objDto.getEmail());
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
