package com.example.cabineperola.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.example.cabineperola.domain.Agendamento;
import com.example.cabineperola.domain.Cliente;

public interface EmailService {

	void sendOrderConfirmationEmail(Agendamento obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Agendamento obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
	
}
