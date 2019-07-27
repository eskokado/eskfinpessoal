package br.com.eskinfotechweb.eskfinpessoal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.repositories.pessoas.PessoaRepositoryQuery;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}
