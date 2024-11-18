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

import jakarta.persistence.NoResultException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.Session;
import mg.breadOnBoard.service.AccountNotFoundException;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.FileIsEmptyException;
import mg.breadOnBoard.service.ImageService;
import mg.breadOnBoard.service.RecipeNotFoundException;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;
import mg.breadOnBoard.service.SessionNotFoundException;
import mg.breadOnBoard.service.SessionService;

@RestController
@CrossOrigin
public class RecipeRestController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeStepService recipeStepService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/api/recipe/get-all/")
	public Iterable<Recipe> getAll() {
		
		return recipeService.findAll();
		
	}
	
	@PostMapping("/api/recipe/search/")
	public Iterable<Recipe> search(@RequestParam String search) {
		
		return recipeService.findAllByTitleOrIngredients(search);
		
	}
	
	@GetMapping("/api/recipe/get-one/{id}")
	public ResponseEntity<Recipe> getOne(@PathVariable String id) {
		
		ResponseEntity<Recipe> response = null;
		Recipe recipe = null;
		
		try {
			
			recipe = recipeService.findOneById(id);
			response = new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
			
		} catch (RecipeNotFoundException e) {

			response = new ResponseEntity<Recipe>(recipe, HttpStatus.NOT_FOUND);
			
		} return response;
		
	}
	
	@PostMapping("/api/recipe/create")
	public ResponseEntity<String> create(@RequestParam String token, @RequestParam String title, @RequestParam MultipartFile image, @RequestParam String ingredients) {
		
		ResponseEntity<String> response = null;
		Recipe recipe = new Recipe();
		
		try {
			
			Session session = sessionService.findById(token);
			Account account = accountService.findById(session.getAccountId());
			recipe.setAccountId(account.getId());
			recipe.setTitle(title);
			recipe.setImage(image.getOriginalFilename());
			recipe.setIngredients(ingredients);
			recipe = recipeService.save(recipe);
			imageService.upload(image);
			
			response = new ResponseEntity<String>(recipe.getId(), HttpStatus.OK);
			
		} catch (FileIsEmptyException | IOException e) {

			response = new ResponseEntity<String>(recipe.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (SessionNotFoundException | AccountNotFoundException e) {

			response = new ResponseEntity<String>("Session introuvable !", HttpStatus.NOT_FOUND);
			
		} return response;
		
	}
	
	@PostMapping("/api/recipe/edit/{id}")
	public ResponseEntity<String> edit(@PathVariable String id, @RequestParam String token, @RequestParam String title, @RequestParam MultipartFile image, @RequestParam String ingredients) {
		
		ResponseEntity<String> response = null;
		Recipe recipe = new Recipe();
		
		try {
			
			recipe = this.tryToEdit(recipe, token, id, title, image, ingredients);
			return new ResponseEntity<String>(recipe.getId(), HttpStatus.OK);
			
		} catch (FileIsEmptyException | IOException e) {

			response = new ResponseEntity<String>(recipe.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (SessionNotFoundException | AccountNotFoundException e) {

			response = new ResponseEntity<String>("Session introuvable !", HttpStatus.NOT_FOUND);
			
		} catch (NoResultException e) {

			response = new ResponseEntity<String>("Recette introuvable !", HttpStatus.NOT_FOUND);
			
		} return response;
		
	}
	
	private Recipe tryToEdit(Recipe recipe, String token, String id, String title, MultipartFile image, String ingredients) throws SessionNotFoundException, AccountNotFoundException, FileIsEmptyException, IOException {
		
		Session session = sessionService.findById(token);
		Account account = accountService.findById(session.getAccountId());
		recipe = recipeService.findByIdAndAccountId(id, account.getId());
		
		if(recipe == null)
			throw new NoResultException();
		
		recipe.setTitle(title);
		recipe.setIngredients(ingredients);
		
		if(!image.getOriginalFilename().equals(recipe.getImage())) {

			String oldImage = recipe.getImage();
			recipe.setImage(image.getOriginalFilename());
			imageService.upload(image);
			imageService.delete(oldImage);
			
		} return recipeService.save(recipe);
		
	}
	
	@PostMapping("/api/recipe/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id, @RequestParam String token) {
		
		ResponseEntity<String> response = null;
		
		try {
				
			Session session = sessionService.findById(token);
			Account account = accountService.findById(session.getAccountId());
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			
			if(recipe == null)
				throw new NoResultException();
			
			recipeStepService.deleteAllByRecipeId(id);
			recipeService.delete(recipe);
			imageService.delete(recipe.getImage());
			response = new ResponseEntity<String>("La recette a bien été supprimée !", HttpStatus.OK);
		
		} catch (SessionNotFoundException | AccountNotFoundException e) {

			response = new ResponseEntity<String>("Vous n'êtes pas connecté !", HttpStatus.NOT_FOUND);
			
		} catch(NoResultException e) {
			
			response = new ResponseEntity<String>("Votre recette est introuvable !", HttpStatus.NOT_FOUND);
			
		} catch (IOException e) {
			
			response = new ResponseEntity<String>("L'image de la recette est introuvable !", HttpStatus.NOT_FOUND);
			
		} return response;
		
	}
	
	@PostMapping("/api/recipe/author/{id}")
	public ResponseEntity<Boolean> isAuthor(@PathVariable String id, @RequestParam String token) {
		
		ResponseEntity<Boolean> response = null;
		
		try {
		
			Session session = sessionService.findById(token);
			Account account = accountService.findById(session.getAccountId());
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			
			if(recipe == null)
				throw new NoResultException();
			
			response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		} catch(SessionNotFoundException | AccountNotFoundException | NoResultException e) {
			
			response = new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			
		} return response;
		
	}

}
