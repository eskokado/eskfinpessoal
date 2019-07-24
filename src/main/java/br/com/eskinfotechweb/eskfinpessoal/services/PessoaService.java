package br.com.eskinfotechweb.eskfinpessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.repositories.PessoaRepository;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.DataIntegrityException;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.ObjectNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));
	}
	
	public Pessoa insert(Pessoa pessoa) {
		pessoa.setId(null);
		Pessoa pessoaInsert = pessoaRepository.save(pessoa);
		return pessoaInsert;
	}
	
	public Pessoa update(Long id, Pessoa pessoa) {
		Pessoa pessoaUpdate = findById(id);
		BeanUtils.copyProperties(pessoa, pessoaUpdate, "id");
		
		return pessoaRepository.save(pessoaUpdate);
	}
	
	public void delete(Long id) {
		Pessoa pessoa = findById(id);
		try {
			pessoaRepository.delete(pessoa);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas! Id: " + id
					+ ", Tipo: " + Pessoa.class.getName());

		}
	}
}
