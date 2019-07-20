package br.com.eskinfotechweb.eskfinpessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Categoria;
import br.com.eskinfotechweb.eskfinpessoal.repositories.CategoriaRepository;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.DataIntegrityException;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Categoria findById(Long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		Categoria categoriaInsert = categoriaRepository.save(categoria);

		return categoriaInsert;
	}
	
	public Categoria update(Long id, Categoria categoria) {
		Categoria categoriaUpdate = findById(id);
		BeanUtils.copyProperties(categoria, categoriaUpdate, "id");
		
		return categoriaRepository.save(categoriaUpdate);
	}
	
	public void delete(Long id) {
		Categoria categoria = findById(id);
		try {
			categoriaRepository.delete(categoria);			
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui lançamento! Id: " + id
					+ ", Tipo: " + Categoria.class.getName());
		}
		
	}

}
