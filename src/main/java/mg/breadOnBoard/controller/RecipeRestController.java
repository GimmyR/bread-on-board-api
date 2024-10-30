package mg.breadOnBoard.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.FileIsEmptyException;
import mg.breadOnBoard.service.ImageService;
import mg.breadOnBoard.service.RecipeNotFoundException;
import mg.breadOnBoard.service.RecipeService;

@RestController
@CrossOrigin
public class RecipeRestController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/api/recipe/get-all/")
	public Iterable<Recipe> getAll() {
		
		return recipeService.findAll();
		
	}
	
	@GetMapping("/api/recipe/get-one/{id}")
	public ResponseEntity<Recipe> getOne(@PathVariable String id) {
		
		ResponseEntity<Recipe> response = null;
		
		try {
			
			Recipe recipe = recipeService.findOneById(id);
			response = new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
			
		} catch (RecipeNotFoundException e) {

			response = new ResponseEntity<Recipe>(new Recipe(), HttpStatus.NOT_FOUND);
			
		} return response;
		
	}
	
	@PostMapping("/api/recipe/create")
	public ResponseEntity<String> create(@RequestParam String title, @RequestParam MultipartFile image, @RequestParam String ingredients) {
		
		Recipe recipe = new Recipe();
		recipe.setAccountId("A0001"); // On verifiera le compte plus tard
		recipe.setTitle(title);
		recipe.setImage(image.getOriginalFilename());
		recipe.setIngredients(ingredients);
		recipe = recipeService.save(recipe);
		
		try {
			
			imageService.upload(image);
			return new ResponseEntity<String>(recipe.getId(), HttpStatus.OK);
			
		} catch (FileIsEmptyException | IOException e) {

			return new ResponseEntity<String>(recipe.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}

}
