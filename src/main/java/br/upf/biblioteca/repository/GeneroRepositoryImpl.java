package br.upf.biblioteca.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public abstract class GeneroRepositoryImpl implements GeneroRepository {

	@PersistenceContext
	private EntityManager em;
	
}
