package com.agendamento.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.agendamento.services.DBService;
import com.agendamento.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instatiateDataBase() throws ParseException {
		dbService.InstatiateTestDataBase();
		return true;
	}

	@Bean
	public MockEmailService emailService() {
		return new MockEmailService();
	}
}
