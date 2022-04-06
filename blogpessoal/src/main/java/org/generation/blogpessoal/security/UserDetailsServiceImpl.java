package org.generation.blogpessoal.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private org.generation.blogpessoal.repository.usuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		Optional<org.generation.blogpessoal.model.usuario> usuario = usuarioRepository.findAllByUsuarioContainingIgnoreCase(userName);
				
	usuario.orElseThrow(() -> new UsernameNotFoundException(userName + "Usuário não existe"));
	
	return usuario.map(UserDetailsImpl::new).get();
				
	}
	
	
	
}

