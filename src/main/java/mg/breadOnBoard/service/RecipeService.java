package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Iterable<Recipe> findAll() {
		
		TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r ORDER BY r.id ASC", Recipe.class);
		
		return query.getResultList();
		
	}
	
	public Iterable<Recipe> findAllByAccountId(String accountId) {
		
		TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.accountId = :account ORDER BY r.id ASC", Recipe.class);
		query.setParameter("account", accountId);
		
		return query.getResultList();
		
	}
	
	public Recipe findOneById(String id) throws RecipeNotFoundException {
		
		Optional<Recipe> opt = recipeRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new RecipeNotFoundException();
		
	}
	
	public Recipe save(Recipe recipe) {
		
		// il faut modifier cette methode pour ajouter l'upload de l'image de la recette
		// et la suppression de l'ancienne image si c'est une modification
		
		return recipeRepository.save(recipe);
		
	}
	
	public void delete(Recipe recipe) {
		
		recipeRepository.delete(recipe);
		
	}

}
