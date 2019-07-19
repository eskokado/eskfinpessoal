package br.com.eskinfotechweb.eskfinpessoal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Categoria;
import br.com.eskinfotechweb.eskfinpessoal.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
}
