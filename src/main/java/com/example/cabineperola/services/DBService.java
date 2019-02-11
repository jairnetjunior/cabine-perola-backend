package com.example.cabineperola.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cabineperola.domain.Agendamento;
import com.example.cabineperola.domain.AgendamentoProcedimento;
import com.example.cabineperola.domain.Cidade;
import com.example.cabineperola.domain.Cliente;
import com.example.cabineperola.domain.Endereco;
import com.example.cabineperola.domain.Estado;
import com.example.cabineperola.domain.Funcionario;
import com.example.cabineperola.domain.Procedimento;
import com.example.cabineperola.domain.enums.Perfil;
import com.example.cabineperola.repositories.AgendamentoProcedimentoRepository;
import com.example.cabineperola.repositories.AgendamentoRepository;
import com.example.cabineperola.repositories.CidadeRepository;
import com.example.cabineperola.repositories.ClienteRepository;
import com.example.cabineperola.repositories.EnderecoRepository;
import com.example.cabineperola.repositories.EstadoRepository;
import com.example.cabineperola.repositories.FuncionarioRepository;
import com.example.cabineperola.repositories.ProcedimentoRepository;

@Service
public class DBService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ProcedimentoRepository procedimentoRepository;
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired AgendamentoProcedimentoRepository agendamentoProcedimentoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void InstatiateTestDataBase() throws ParseException {
				
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "72211548644", "Maria Silva", "maria@gmail.com", pe.encode("123"));
		cli1.setTelefones(Arrays.asList("27363323", "93838393").stream().collect(Collectors.toSet()));		
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		
		
		cli1.setEnderecos(Arrays.asList(e1));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Agendamento ag1 = new Agendamento(null, sdf.parse("30/09/2017 12:32"), cli1);
		
		Funcionario fun1 = new Funcionario(null, "43115550243", "Fernando Gatinho", "fegato@gmail.com", pe.encode("123"));
		fun1.setTelefones(Arrays.asList("27363323", "93838393").stream().collect(Collectors.toSet()));
		fun1.addPerfil(Perfil.ADMIN);
		
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", fun1, c2);
		
		fun1.setEnderecos(Arrays.asList(e2));
		
		Procedimento pro1 = new Procedimento(null, "Depilação", "Depilação a cera", 20.00);
		pro1.setFuncionario(fun1);
		
		AgendamentoProcedimento ap1 = new AgendamentoProcedimento(ag1, pro1, 1, 20.00);
		
				
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		funcionarioRepository.saveAll(Arrays.asList(fun1));
		
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		procedimentoRepository.saveAll(Arrays.asList(pro1));
		agendamentoRepository.saveAll(Arrays.asList(ag1));
		
		agendamentoProcedimentoRepository.saveAll(Arrays.asList(ap1));		
	}
}
