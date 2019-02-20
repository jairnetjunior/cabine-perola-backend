package com.agendamento.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agendamento.domain.Agendamento;
import com.agendamento.domain.AgendamentoProcedimento;
import com.agendamento.domain.Cidade;
import com.agendamento.domain.Cliente;
import com.agendamento.domain.Endereco;
import com.agendamento.domain.Estado;
import com.agendamento.domain.Funcionario;
import com.agendamento.domain.Procedimento;
import com.agendamento.domain.enums.Perfil;
import com.agendamento.repositories.AgendamentoProcedimentoRepository;
import com.agendamento.repositories.AgendamentoRepository;
import com.agendamento.repositories.CidadeRepository;
import com.agendamento.repositories.ClienteRepository;
import com.agendamento.repositories.EnderecoRepository;
import com.agendamento.repositories.EstadoRepository;
import com.agendamento.repositories.FuncionarioRepository;
import com.agendamento.repositories.ProcedimentoRepository;

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

	@Autowired
	AgendamentoProcedimentoRepository agendamentoProcedimentoRepository;

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

		Funcionario fun1 = new Funcionario(null, "43115550243", "Fernando Gatinho", "fegato@gmail.com",
				pe.encode("123"));
		fun1.setTelefones(Arrays.asList("27363323", "93838393").stream().collect(Collectors.toSet()));
		fun1.addPerfil(Perfil.ADMIN);

		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", fun1, c2);

		fun1.setEnderecos(Arrays.asList(e2));

		Procedimento pro1 = new Procedimento(null, "Depilação", "Depilação a cera", 20.00);
		pro1.setFuncionario(fun1);
		Procedimento pro2 = new Procedimento(null, "Massagem", "Massagem Relaxante", 30.00);
		pro2.setFuncionario(fun1);
		Procedimento pro3 = new Procedimento(null, "Sobrancelhas", "Sobrancelha definitiva", 210.00);
		pro3.setFuncionario(fun1);
		Procedimento pro4 = new Procedimento(null, "Mãos", "Mãos completa", 40.00);
		pro4.setFuncionario(fun1);
		Procedimento pro5 = new Procedimento(null, "Pés", "Pés completa", 40.00);
		pro5.setFuncionario(fun1);
		Procedimento pro6 = new Procedimento(null, "Mãos e Pés", "Mãos e Pés Completa", 70.00);
		pro6.setFuncionario(fun1);
		Procedimento pro7 = new Procedimento(null, "Hidratação", "Hidratação cabelo", 80.00);
		pro7.setFuncionario(fun1);
		Procedimento pro8 = new Procedimento(null, "Selagem", "Selagem restauradora", 210.00);
		pro8.setFuncionario(fun1);

		AgendamentoProcedimento ap1 = new AgendamentoProcedimento(ag1, pro1, 1, 20.00);

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		clienteRepository.saveAll(Arrays.asList(cli1));

		funcionarioRepository.saveAll(Arrays.asList(fun1));

		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		procedimentoRepository.saveAll(Arrays.asList(pro1, pro2, pro3, pro4, pro5, pro6, pro7, pro8));
		agendamentoRepository.saveAll(Arrays.asList(ag1));

		agendamentoProcedimentoRepository.saveAll(Arrays.asList(ap1));
	}
}
