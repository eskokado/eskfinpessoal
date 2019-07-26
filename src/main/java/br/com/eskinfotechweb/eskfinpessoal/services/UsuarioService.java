package br.com.eskinfotechweb.eskfinpessoal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Usuario;
import br.com.eskinfotechweb.eskfinpessoal.repositories.UsuarioRepository;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario findById(Long id) {
		Optional<Usuario> obj = usuarioRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário e/ou senha incorretos"));
	}
	
	public Usuario findByEmail(String email) {
		Optional<Usuario> obj = usuarioRepository.findByEmail(email);
		return obj.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
	}
	
}
