package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.repository.RecipeStepRepository;

@Service
public class RecipeStepService {
	
	@Autowired
	private RecipeStepRepository recipeStepRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private SequenceService sequenceService;
	
	public Iterable<RecipeStep> findAllByRecipeId(String recipeId) {
		
		TypedQuery<RecipeStep> query = entityManager.createQuery("SELECT rs FROM RecipeStep rs WHERE rs.recipeId = :recipe ORDER BY rs.id ASC", RecipeStep.class);
		query.setParameter("recipe", recipeId);
		
		return query.getResultList();
		
	}
	
	public RecipeStep findOneById(String id) throws RecipeStepNotFoundException {
		
		Optional<RecipeStep> opt = recipeStepRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new RecipeStepNotFoundException();
		
	}
	
	public RecipeStep save(RecipeStep step) {
		
		if(step.getId() == null) {
			
			String id = sequenceService.generateRecipeStepID();
			step.setId(id);
			
		} return recipeStepRepository.save(step);
		
	}
	
	public void saveAll(String recipeId, Iterable<RecipeStep> steps) {
		
		int order = 1;
		
		for(RecipeStep step : steps) {
			
			step.setOrder(order);
			step.setRecipeId(recipeId);
			this.save(step);
			order++;
			
		}
		
	}
	
	public void delete(RecipeStep step) {
		
		recipeStepRepository.delete(step);
		
	}
	
	@Transactional
	public void deleteAllByRecipeId(String recipeId) {
		
		Query query = entityManager.createQuery("DELETE FROM RecipeStep rs WHERE rs.recipeId = :recipeId");
		query.setParameter("recipeId", recipeId);
		query.executeUpdate();
		
	}

}
