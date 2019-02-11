package com.example.cabineperola.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cabineperola.domain.Estado;
import com.example.cabineperola.repositories.EstadoRepository;


@Service
public class EstadoService {
	@Autowired
	private EstadoRepository estadoRepository;
	
	
	public List<Estado> findAll(){
		return estadoRepository.findAllByOrderByNome();
	}
	
	

}
