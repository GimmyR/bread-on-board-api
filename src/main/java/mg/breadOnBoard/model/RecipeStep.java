package mg.breadOnBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipe_step")
public class RecipeStep {
	
	public static final String PREFIX = "S";
	
	public static final int LENGTH_ID = 6;
	
	// ATTRIBUTES :
	
	@Id
	private String id;
	
	@Column(name = "recipe_id")
	private String recipeId;
	
	@Column(name = "step_order")
	private int order;
	
	private String text;
	
	// GETTERS AND SETTERS :
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
