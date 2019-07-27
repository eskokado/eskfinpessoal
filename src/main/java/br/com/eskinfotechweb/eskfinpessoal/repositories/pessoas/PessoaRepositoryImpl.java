package br.com.eskinfotechweb.eskfinpessoal.repositories.pessoas;

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

import br.com.eskinfotechweb.eskfinpessoal.domain.Endereco_;
import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa_;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Pessoa> search(PessoaFilter pessoaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);

		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
				
		return query.getResultList();
	}

	@Override
	public Page<Pessoa> page(PessoaFilter pessoaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
	}

	private Long total(PessoaFilter pessoaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(
						builder.lower(root.get(Pessoa_.nome)),
						"%" + pessoaFilter.getNome().toLowerCase() + "%"
					));
		}

		if(!StringUtils.isEmpty(pessoaFilter.getCidade())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.endereco).get(Endereco_.cidade)),
					"%" + pessoaFilter.getCidade().toLowerCase() + "%"
				));
		}

		if(!StringUtils.isEmpty(pessoaFilter.getEstado())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.endereco).get(Endereco_.estado)),
					"%" + pessoaFilter.getEstado().toLowerCase() + "%"
				));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
}
