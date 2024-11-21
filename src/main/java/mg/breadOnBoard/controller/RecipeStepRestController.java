package mg.breadOnBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.NoResultException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.service.AccountNotFoundException;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;

@RestController
@CrossOrigin
public class RecipeStepRestController {
	
	@Autowired
	private RecipeStepService recipeStepService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/api/recipe-step/get-all/{recipeId}")
	public Iterable<RecipeStep> getAllByRecipeId(@PathVariable String recipeId) {
		
		return recipeStepService.findAllByRecipeId(recipeId);
		
	}
	
	@PostMapping("/api/recipe-step/save-all")
	public ResponseEntity<String> saveAll(@RequestHeader("Authorization") String authorization, @RequestBody StepsForm body) {
		
		ResponseEntity<String> response = null;
		
		try {
			
			Account account = accountService.getAccountByJWT(authorization);
			Recipe recipe = recipeService.findByIdAndAccountId(body.getRecipeId(), account.getId());
			recipeStepService.saveAll(recipe.getId(), body.getSteps());
			response = new ResponseEntity<String>(body.getRecipeId(), HttpStatus.OK);
		
		} catch (AccountNotFoundException e) {
			
			response = new ResponseEntity<String>("Compte introuvable !", HttpStatus.NOT_FOUND);
			
		} catch (NoResultException e) {
			
			response = new ResponseEntity<String>("Recette introuvable !", HttpStatus.NOT_FOUND);
			
		} return response;
		
	}

}
