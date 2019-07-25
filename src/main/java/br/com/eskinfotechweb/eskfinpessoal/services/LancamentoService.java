package br.com.eskinfotechweb.eskfinpessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.repositories.LancamentoRepository;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.ObjectNotFoundException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}
	
	public Lancamento findById(Long id) {
		Optional<Lancamento> obj = lancamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Lancamento.class.getName()));
	}
	
	public Lancamento insert(Lancamento lancamento) {
		lancamento.setId(null);
		Lancamento lancamentoInsert = lancamentoRepository.save(lancamento);
		return lancamentoInsert;
	}
}
