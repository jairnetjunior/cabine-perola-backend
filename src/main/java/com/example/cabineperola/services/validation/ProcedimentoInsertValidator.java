package com.example.cabineperola.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cabineperola.domain.Procedimento;
import com.example.cabineperola.dto.ProcedimentoNewDTO;
import com.example.cabineperola.repositories.ProcedimentoRepository;
import com.example.cabineperola.resources.exceptions.FieldMessage;

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
