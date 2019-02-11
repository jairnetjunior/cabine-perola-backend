package com.example.cabineperola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cabineperola.domain.Cliente;
import com.example.cabineperola.domain.Funcionario;
import com.example.cabineperola.repositories.ClienteRepository;
import com.example.cabineperola.repositories.FuncionarioRepository;
import com.example.cabineperola.security.UserSS;




@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepository.findByEmail(email);
		Funcionario fun = funcionarioRepository.findByEmail(email);
		if (cli == null && fun == null) {
			throw new UsernameNotFoundException(email);
		}
		else if(cli != null){
			return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
		}
		else {
			return new UserSS(fun.getId(), fun.getEmail(), fun.getSenha(), fun.getPerfis());
		}
	}
}