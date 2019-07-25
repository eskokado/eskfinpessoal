package br.com.eskinfotechweb.eskfinpessoal.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.eskinfotechweb.eskfinpessoal.domain.Endereco;
import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	public ResponseEntity<List<Pessoa>> findAll() {
		List<Pessoa> pessoas = pessoaService.findAll();
		return ResponseEntity.ok(pessoas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.findById(id);
		return ResponseEntity.ok(pessoa);
	}

	@PostMapping
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaInsert = pessoaService.insert(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pessoaInsert.getId())
				.toUri();
		return ResponseEntity.created(uri).body(pessoaInsert);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaUpdate = pessoaService.update(id, pessoa);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	@PutMapping("/{id}/ativo")
	public ResponseEntity<Pessoa> updateAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		Pessoa pessoaUpdate = pessoaService.updateAtivo(id, ativo);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	@PutMapping("/{id}/endereco")
	public ResponseEntity<Pessoa> updateEndereco(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
		Pessoa pessoaUpdate = pessoaService.updateEndereco(id, endereco);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		pessoaService.delete(id);
	}
}
