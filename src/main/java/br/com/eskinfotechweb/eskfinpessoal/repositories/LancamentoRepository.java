package br.com.eskinfotechweb.eskfinpessoal.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

	List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
	
}
