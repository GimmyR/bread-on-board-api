package mg.breadOnBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.RecipeService;

@RestController
public class RecipeRestController {
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/api/recipe/get-all/")
	public Iterable<Recipe> getAll() {
		
		return recipeService.findAll();
		
	}

}
