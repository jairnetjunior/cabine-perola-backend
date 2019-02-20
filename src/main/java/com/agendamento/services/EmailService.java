package com.agendamento.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.agendamento.domain.Agendamento;
import com.agendamento.domain.Cliente;

public interface EmailService {

	void sendOrderConfirmationEmail(Agendamento obj);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(Agendamento obj);

	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);

}
