package br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> search(LancamentoFilter lancamentoFilter);
	public Page<Lancamento> page(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
