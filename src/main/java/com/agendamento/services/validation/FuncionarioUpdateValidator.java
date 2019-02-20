package com.agendamento.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.agendamento.domain.Funcionario;
import com.agendamento.dto.FuncionarioDTO;
import com.agendamento.repositories.FuncionarioRepository;
import com.agendamento.resources.exceptions.FieldMessage;

public class FuncionarioUpdateValidator implements ConstraintValidator<FuncionarioUpdate, FuncionarioDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public void initialize(FuncionarioUpdate ann) {
	}

	@Override
	public boolean isValid(FuncionarioDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Funcionario aux = funcionarioRepository.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId))
			list.add(new FieldMessage("email", "Email já existente"));

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
