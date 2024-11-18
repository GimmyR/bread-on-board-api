package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.repository.RecipeStepRepository;

@Service
public class RecipeStepService {
	
	@Autowired
	private RecipeStepRepository recipeStepRepository;
	
	@Autowired
	private SequenceService sequenceService;
	
	public Iterable<RecipeStep> findAllByRecipeId(String recipeId) {
		
		return recipeStepRepository.findAllByRecipeId(recipeId, Sort.by(Sort.Direction.ASC, "order"));
		
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
	
	public void deleteAllByRecipeId(String recipeId) {
		
		recipeStepRepository.deleteAllByRecipeId(recipeId);
		
	}

}
