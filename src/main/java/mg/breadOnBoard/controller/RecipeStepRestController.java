package mg.breadOnBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.service.RecipeStepService;

@RestController
public class RecipeStepRestController {
	
	@Autowired
	private RecipeStepService recipeStepService;
	
	@GetMapping("/api/recipe-step/get-all/{recipeId}")
	public Iterable<RecipeStep> getAllByRecipeId(@PathVariable String recipeId) {
		
		return recipeStepService.findAllByRecipeId(recipeId);
		
	}

}
