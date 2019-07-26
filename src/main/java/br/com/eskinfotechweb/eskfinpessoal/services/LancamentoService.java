package br.com.eskinfotechweb.eskfinpessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.repositories.LancamentoRepository;
import br.com.eskinfotechweb.eskfinpessoal.repositories.PessoaRepository;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.LancamentoFilter;
import br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.projection.ResumoLancamento;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.DataIntegrityException;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.ObjectNotFoundException;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}
	
	public Lancamento findById(Long id) {
		Optional<Lancamento> obj = lancamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Lancamento.class.getName()));
	}
	
	public List<Lancamento> search(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.search(lancamentoFilter);
	}
	
	public Page<Lancamento> page(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.page(lancamentoFilter, pageable);
	}

	public Page<ResumoLancamento> resum(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resum(lancamentoFilter, pageable);
	}
	
	public Lancamento insert(Lancamento lancamento) {
		if (!pessoaRepository.existsById(lancamento.getPessoa().getId())) {
			throw new PessoaInexistenteOuInativaException("Pessoa Inexistente");			
		}
		Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getId());				
		if (pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException("Pessoa Inativa");
		}
		lancamento.setId(null);
		Lancamento lancamentoInsert = lancamentoRepository.save(lancamento);
		return lancamentoInsert;
	}
	
	public Lancamento update(Long id, Lancamento lancamento) {
		Lancamento lancamentoUpdate = findById(id);
		BeanUtils.copyProperties(lancamento, lancamentoUpdate, "id");
		return lancamentoRepository.save(lancamentoUpdate);
	}
	
	public void delete(Long id) {
		Lancamento lancamento = findById(id);
		try {			
			lancamentoRepository.delete(lancamento);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas! Id: " + id
					+ ", Tipo: " + Lancamento.class.getName());
		}
	}
}
