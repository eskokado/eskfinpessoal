package br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.eskinfotechweb.eskfinpessoal.domain.Categoria_;
import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento_;
import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa_;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoria;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoriaTipo;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaDiaTipo;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaPessoa;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.LancamentoFilter;
import br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> search(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
//		Join<Lancamento, Pessoa> joinPessoa = root.join(Lancamento_.pessoa);
//		Join<Lancamento, Categoria> joinCategoria = root.join(Lancamento_.categoria);

		// Criar restrições
//		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root, joinPessoa, joinCategoria);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public Page<Lancamento> page(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
//		Join<Lancamento, Pessoa> joinPessoa = root.join(Lancamento_.pessoa);
//		Join<Lancamento, Categoria> joinCategoria = root.join(Lancamento_.categoria);

		// Criar restrições
//		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root, joinPessoa, joinCategoria);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	@Override
	public Page<ResumoLancamento> resum(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
//		Join<Lancamento, Pessoa> joinPessoa = root.join(Lancamento_.pessoa);
//		Join<Lancamento, Categoria> joinCategoria = root.join(Lancamento_.categoria);

		criteria.select(builder.construct(ResumoLancamento.class, 
				root.get(Lancamento_.id), root.get(Lancamento_.descricao), 
				root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento), 
				root.get(Lancamento_.valor), root.get(Lancamento_.tipo),
				root.get(Lancamento_.categoria).get(Categoria_.nome), 
				root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

		// Criar restrições
//		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root, joinPessoa, joinCategoria);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

		criteria.where(predicates);

		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
//		Join<Lancamento, Pessoa> joinPessoa = root.join(Lancamento_.pessoa);
//		Join<Lancamento, Categoria> joinCategoria = root.join(Lancamento_.categoria);

		// Criar restrições
//		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root, joinPessoa, joinCategoria);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

//	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
//			Root<Lancamento> root, Join<Lancamento, Pessoa> joinPessoa, Join<Lancamento, Categoria> joinCategoria) {
//		List<Predicate> predicates = new ArrayList<>();

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}

//		if (!StringUtils.isEmpty(lancamentoFilter.getNomePessoa())) {
//			predicates.add(builder.like(
//						builder.lower(builder.treat(joinPessoa, Pessoa.class).get(Pessoa_.nome)),
//						"%" + lancamentoFilter.getNomePessoa().toLowerCase() + "%"
//					));
//		}

		if (!StringUtils.isEmpty(lancamentoFilter.getNomePessoa())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.pessoa).get(Pessoa_.nome)),
					"%" + lancamentoFilter.getNomePessoa().toLowerCase() + "%"));
		}

//		if (!StringUtils.isEmpty(lancamentoFilter.getNomeCategoria())) {
//			predicates.add(builder.like(
//						builder.lower(builder.treat(joinCategoria, Categoria.class).get(Categoria_.nome)), 
//						"%" + lancamentoFilter.getNomeCategoria().toLowerCase() + "%"
//					));
//		}

		if (!StringUtils.isEmpty(lancamentoFilter.getNomeCategoria())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.categoria).get(Categoria_.nome)),
					"%" + lancamentoFilter.getNomeCategoria().toLowerCase() + "%"));
		}

		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDataVencimentoDe()));
		}

		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDataVencimentoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate dataDe, LocalDate dataAte) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaCategoria> criteria = builder.createQuery(LancamentoEstatisticaCategoria.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(
					LancamentoEstatisticaCategoria.class,
					root.get(Lancamento_.categoria),
					builder.sum(root.get(Lancamento_.valor))
				));
		
		criteria.where(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataDe),
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataAte)
				);
		criteria.groupBy(root.get(Lancamento_.categoria));
		
		TypedQuery<LancamentoEstatisticaCategoria> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public List<LancamentoEstatisticaDiaTipo> porDiaTipo(LocalDate dataDe, LocalDate dataAte) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaDiaTipo> criteria = builder.createQuery(LancamentoEstatisticaDiaTipo.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(
					LancamentoEstatisticaDiaTipo.class,
					root.get(Lancamento_.tipo),
					root.get(Lancamento_.dataVencimento),
					builder.sum(root.get(Lancamento_.VALOR))
				));
		
		criteria.where(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataDe),
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataAte)
				);
		criteria.groupBy(root.get(Lancamento_.tipo), root.get(Lancamento_.dataVencimento));
		
		TypedQuery<LancamentoEstatisticaDiaTipo> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public List<LancamentoEstatisticaCategoriaTipo> porCategoriaTipo(LocalDate dataDe, LocalDate dataAte) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaCategoriaTipo> criteria = builder.createQuery(LancamentoEstatisticaCategoriaTipo.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(
				LancamentoEstatisticaCategoriaTipo.class,
				root.get(Lancamento_.categoria),
				root.get(Lancamento_.tipo),
				builder.sum(root.get(Lancamento_.VALOR))
			));

		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataDe),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataAte)
			);
		criteria.groupBy(root.get(Lancamento_.categoria), root.get(Lancamento_.tipo));
		
		TypedQuery<LancamentoEstatisticaCategoriaTipo> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate dataDe, LocalDate dataAte) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaPessoa> criteria = builder.createQuery(LancamentoEstatisticaPessoa.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(
				LancamentoEstatisticaPessoa.class,
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa),
				builder.sum(root.get(Lancamento_.VALOR))
			));

		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataDe),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataAte)
			);
		criteria.groupBy(root.get(Lancamento_.tipo), root.get(Lancamento_.pessoa));
		
		TypedQuery<LancamentoEstatisticaPessoa> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

}
