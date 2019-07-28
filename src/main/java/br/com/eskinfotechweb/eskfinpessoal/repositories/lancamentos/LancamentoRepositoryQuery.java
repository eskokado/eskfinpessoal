package br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoria;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoriaTipo;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaDiaTipo;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.LancamentoFilter;
import br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public List<LancamentoEstatisticaCategoriaTipo> porCategoriaTipo(LocalDate dataDe, LocalDate dataAte);
	public List<LancamentoEstatisticaDiaTipo> porDiaTipo(LocalDate dataDe, LocalDate dataAte);
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate dataDe, LocalDate dataAte);
	
	public List<Lancamento> search(LancamentoFilter lancamentoFilter);
	public Page<Lancamento> page(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resum(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
