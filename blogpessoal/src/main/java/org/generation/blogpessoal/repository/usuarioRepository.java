package org.generation.blogpessoal.repository;

import java.util.Optional;

import org.generation.blogpessoal.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long>{

	public Optional<usuario> findAllByUsuarioContainingIgnoreCase(String usuario);
	
}
