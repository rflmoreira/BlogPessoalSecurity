package org.generation.blogpessoal.repository;

import java.util.List;

import org.generation.blogpessoal.model.tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface temaRepository extends JpaRepository<tema, Long>{

	public List<tema> findAllByNomeContainingIgnoreCase(String nome);	
}
