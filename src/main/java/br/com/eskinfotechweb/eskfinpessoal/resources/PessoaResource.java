package br.com.eskinfotechweb.eskfinpessoal.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.PessoaFilter;
import br.com.eskinfotechweb.eskfinpessoal.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Pessoa>> findAll() {
		List<Pessoa> pessoas = pessoaService.findAll();
		return ResponseEntity.ok(pessoas);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.findById(id);
		return ResponseEntity.ok(pessoa);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Pessoa>> search(PessoaFilter pessoaFilter) {
		List<Pessoa> pessoas = pessoaService.search(pessoaFilter);
		return ResponseEntity.ok(pessoas);
	}
	
	@GetMapping("/page")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Pessoa>> page(PessoaFilter pessoaFilter, Pageable pageable) {
		Page<Pessoa> pessoas = pessoaService.page(pessoaFilter, pageable);
		return ResponseEntity.ok(pessoas);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaInsert = pessoaService.insert(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pessoaInsert.getId())
				.toUri();
		return ResponseEntity.created(uri).body(pessoaInsert);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaUpdate = pessoaService.update(id, pessoa);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	@PutMapping("/{id}/ativo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> updateAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		Pessoa pessoaUpdate = pessoaService.updateAtivo(id, ativo);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	@PutMapping("/{id}/endereco")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> updateEndereco(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
		Pessoa pessoaUpdate = pessoaService.updateEndereco(id, endereco);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		pessoaService.delete(id);
	}
}
