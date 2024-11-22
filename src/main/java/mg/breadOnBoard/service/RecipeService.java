package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.repository.RecipeRepository;

@Service
@Transactional
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private SequenceService sequenceService;
	
	public Iterable<Recipe> findAll() {
		
		return recipeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Iterable<Recipe> findAllByAccountId(String accountId) {
		
		return recipeRepository.findByAccountId(accountId, Sort.by(Sort.Direction.ASC, "accountId"));
		
	}
	
	public Iterable<Recipe> findAllByTitleOrIngredients(String search) {
		
		String toSearch = "%" + search + "%";
		
		return recipeRepository.findByTitleLikeOrIngredientsLike(toSearch, toSearch, Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Recipe findOneById(String id) throws RecipeNotFoundException {
		
		Optional<Recipe> opt = recipeRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new RecipeNotFoundException();
		
	}
	
	public Recipe findByIdAndAccountId(String id, String accountId) {
		
		Recipe recipe = recipeRepository.findOneByIdAndAccountId(id, accountId);
		
		if(recipe == null)
			throw new NoResultException();
		
		return recipe;
		
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
