package com.agendamento.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.agendamento.domain.Procedimento;
import com.agendamento.dto.ProcedimentoNewDTO;
import com.agendamento.repositories.ProcedimentoRepository;
import com.agendamento.resources.exceptions.FieldMessage;

public class ProcedimentoInsertValidator implements ConstraintValidator<ProcedimentoInsert, ProcedimentoNewDTO> {

	@Autowired
	private ProcedimentoRepository procedimentoRepository;

	@Override
	public void initialize(ProcedimentoInsert ann) {
	}

	@Override
	public boolean isValid(ProcedimentoNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Procedimento aux = procedimentoRepository.findByNome(objDto.getNome());
		if (aux != null)
			list.add(new FieldMessage("nome", "Procedimento já existente"));

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
