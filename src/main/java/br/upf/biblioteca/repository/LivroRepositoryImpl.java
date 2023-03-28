package br.upf.biblioteca.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public abstract class LivroRepositoryImpl implements LivroRepository {

	@PersistenceContext
	private EntityManager em;
	
}
