package mg.breadOnBoard.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.RecipeStep;

@Service
public class SequenceService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public String generateAccountID() {
		
		Query query = entityManager.createNativeQuery("SELECT account_seq.nextval FROM dual", Integer.class);
		int seq = (Integer) query.getSingleResult();
		
		return this.generateID(Account.PREFIX, Account.LENGTH_ID, seq);
		
	}
	
	public String generateRecipeID() {
		
		Query query = entityManager.createNativeQuery("SELECT recipe_seq.nextval FROM dual", Integer.class);
		int seq = (Integer) query.getSingleResult();
		
		return this.generateID(Recipe.PREFIX, Recipe.LENGTH_ID, seq);
		
	}
	
	public String generateRecipeStepID() {
		
		Query query = entityManager.createNativeQuery("SELECT recipe_step_seq.nextval FROM dual", Integer.class);
		int seq = (Integer) query.getSingleResult();
		
		return this.generateID(RecipeStep.PREFIX, RecipeStep.LENGTH_ID, seq);
		
	}
	
	private String generateID(String prefix, int lengthID, int sequence) {
		
		String id = "" + sequence;
		
		while(id.length() < lengthID)
			id = 0 + id;
		
		return prefix + id;
		
	}

}
