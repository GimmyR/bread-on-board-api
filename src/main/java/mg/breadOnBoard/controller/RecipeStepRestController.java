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

import jakarta.persistence.NoResultException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.model.Session;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;
import mg.breadOnBoard.service.SessionNotFoundException;
import mg.breadOnBoard.service.SessionService;

@RestController
@CrossOrigin
public class RecipeStepRestController {
	
	@Autowired
	private RecipeStepService recipeStepService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/api/recipe-step/get-all/{recipeId}")
	public Iterable<RecipeStep> getAllByRecipeId(@PathVariable String recipeId) {
		
		return recipeStepService.findAllByRecipeId(recipeId);
		
	}
	
	@PostMapping("/api/recipe-step/save-all")
	public ResponseEntity<String> saveAll(@RequestBody StepsForm body) {
		
		ResponseEntity<String> response = null;
		
		try {
			
			Session session = sessionService.findById(body.getToken());
			Recipe recipe = recipeService.findByIdAndAccountId(body.getRecipeId(), session.getAccountId());
			recipeStepService.saveAll(recipe.getId(), body.getSteps());
			response = new ResponseEntity<String>(body.getRecipeId(), HttpStatus.OK);
		
		} catch (SessionNotFoundException e) {
			
			response = new ResponseEntity<String>("Session introuvable !", HttpStatus.NOT_FOUND);
			
		} catch (NoResultException e) {
			
			response = new ResponseEntity<String>("Recette introuvable !", HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		} return response;
		
	}

}
