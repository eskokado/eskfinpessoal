package br.com.eskinfotechweb.eskfinpessoal.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.services.LancamentoService;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymelead;
	
	@Autowired
	private LancamentoService lancamentoService;
	
//	@EventListener
//	private void teste(ApplicationReadyEvent event) {
//		this.enviarEmail(
//				"edsonskok@gmail.com", 
//				Arrays.asList("eskokado@gmail.com"), 
//				"Testando", 
//				"Olá<br/> Teste ok");
//		System.out.println("Terminado o envio de e-mail...");
//	}
	
	@EventListener
	private void teste(ApplicationReadyEvent evend) {
		String template = "mail/aviso-lancamentos-vencidos.html";
		
		List<Lancamento> lista = lancamentoService.findAll();
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", lista);
		
		this.enviarEmail(
				"edsonskok@gmail.com", 
				Arrays.asList("eskokado@gmail.com"), 
				"Testando Thymeleaf", template, variaveis);
		
		System.out.println("Terminado o envio de e-mail (Thymelead)...");
	}
	
	private void enviarEmail(String remetente, List<String> destinatarios, String assunto, 
			String template, Map<String, Object> variaveis) {
		
		Context context =  new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey() , e.getValue()));
		
		String mensagem = thymelead.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}

	public void enviarEmail(
			String remetente, List<String> destinatarios, String assunto, String mensagem
	) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem);
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail", e);
		}
	}
	
}
