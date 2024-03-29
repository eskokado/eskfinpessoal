package br.com.eskinfotechweb.eskfinpessoal.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoria;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoriaTipo;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaDiaTipo;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.LancamentoFilter;
import br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.projection.ResumoLancamento;
import br.com.eskinfotechweb.eskfinpessoal.services.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Lancamento>> findAll() {
		List<Lancamento> lancamentos = lancamentoService.findAll();
		return ResponseEntity.ok(lancamentos);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> findById(@PathVariable Long id) {
		Lancamento lancamento = lancamentoService.findById(id);
		return ResponseEntity.ok(lancamento);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Lancamento>> search(LancamentoFilter lancamentoFilter) {
		List<Lancamento> lancamentos = lancamentoService.search(lancamentoFilter);
		return ResponseEntity.ok(lancamentos);
	}

	@GetMapping("/page")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Lancamento>> page(LancamentoFilter lancamentoFilter, Pageable pageable) {
		Page<Lancamento> lancamentos = lancamentoService.page(lancamentoFilter, pageable);
		return ResponseEntity.ok(lancamentos);
	}

	@GetMapping("/resum")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<ResumoLancamento>> resum(LancamentoFilter lancamentoFilter, Pageable pageable) {
		Page<ResumoLancamento> resumo = lancamentoService.resum(lancamentoFilter, pageable);
		return ResponseEntity.ok(resumo);
	}

	@GetMapping("/estatisticas/por-categoria")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<LancamentoEstatisticaCategoria>> porCategoria(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataDe,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataAte) {
		List<LancamentoEstatisticaCategoria> dados = lancamentoService.porCategoria(dataDe, dataAte);
		return ResponseEntity.ok(dados);
	}
	
	@GetMapping("/estatisticas/por-dia-tipo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<LancamentoEstatisticaDiaTipo>> porDiaTipo(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataDe,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataAte) {
		List<LancamentoEstatisticaDiaTipo> dados = lancamentoService.porDiaTipo(dataDe, dataAte);
		return ResponseEntity.ok(dados);
	}

	@GetMapping("/estatisticas/por-categoria-tipo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<LancamentoEstatisticaCategoriaTipo>> porCategoriaTipo(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataDe,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataAte) {
		List<LancamentoEstatisticaCategoriaTipo> dados = lancamentoService.porCategoriaTipo(dataDe, dataAte);
		return ResponseEntity.ok(dados);
	}

	@GetMapping("/relatorios/por-pessoa")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioPorPessoa(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataDe, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataAte) throws Exception {
		byte[] relatorio = lancamentoService.relatorioPorPessoa(dataDe, dataAte);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> create(@RequestBody Lancamento lancamento) {
		Lancamento lancamentoInsert = lancamentoService.insert(lancamento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(lancamentoInsert.getId()).toUri();
		return ResponseEntity.created(uri).body(lancamentoInsert);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> update(@PathVariable Long id, @RequestBody Lancamento lancamento) {
		Lancamento lancamentoUpdate = lancamentoService.update(id, lancamento);
		return ResponseEntity.ok(lancamentoUpdate);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		lancamentoService.delete(id);
	}
}
