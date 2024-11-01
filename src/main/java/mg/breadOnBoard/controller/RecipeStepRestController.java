package mg.breadOnBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.service.RecipeStepService;

@RestController
@CrossOrigin
public class RecipeStepRestController {
	
	@Autowired
	private RecipeStepService recipeStepService;
	
	@GetMapping("/api/recipe-step/get-all/{recipeId}")
	public Iterable<RecipeStep> getAllByRecipeId(@PathVariable String recipeId) {
		
		return recipeStepService.findAllByRecipeId(recipeId);
		
	}
	
	@PostMapping("/api/recipe-step/save-all")
	public ResponseEntity<String> saveAll(@RequestBody StepsForm body) {
		
		ResponseEntity<String> response = null;
		
		try {
		
			recipeStepService.saveAll(body.getRecipeId(), body.getSteps());
			response = new ResponseEntity<String>(body.getRecipeId(), HttpStatus.OK);
		
		} catch (Exception e) {
			
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		} return response;
		
	}

}
