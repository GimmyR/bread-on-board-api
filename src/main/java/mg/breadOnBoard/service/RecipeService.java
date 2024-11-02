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
	
	@Autowired
	private SequenceService sequenceService;
	
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
	
	public Recipe findByIdAndAccountId(String id, String accountId) {
		
		TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.id = :id AND r.accountId = :account", Recipe.class);
		query.setParameter("id", id);
		query.setParameter("account", accountId);
		
		return query.getSingleResult();
		
	}
	
	public Recipe save(Recipe recipe) {
		
		if(recipe.getId() == null) {
			
			String id = sequenceService.generateRecipeID();
			recipe.setId(id);
			
		} return recipeRepository.save(recipe);
		
	}
	
	public void delete(Recipe recipe) {
		
		recipeRepository.delete(recipe);
		
	}

}
