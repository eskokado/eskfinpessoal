package br.com.eskinfotechweb.eskfinpessoal.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.services.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> findAll() {
		List<Lancamento> lancamentos = lancamentoService.findAll();
		return ResponseEntity.ok(lancamentos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> findById(@PathVariable Long id) {
		Lancamento lancamento = lancamentoService.findById(id);
		return ResponseEntity.ok(lancamento);
	}
}
