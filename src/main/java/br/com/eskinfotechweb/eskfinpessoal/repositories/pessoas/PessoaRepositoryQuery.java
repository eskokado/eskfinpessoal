package br.com.eskinfotechweb.eskfinpessoal.repositories.pessoas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public List<Pessoa> search(PessoaFilter pessoaFilter);
	public Page<Pessoa> page(PessoaFilter pessoaFilter, Pageable pageable);
	
}
