package mg.breadOnBoard.controller;

import mg.breadOnBoard.model.RecipeStep;

public class StepsForm {
	
	private String recipeId;
	
	private Iterable<RecipeStep> steps;
	
	public String getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	
	public Iterable<RecipeStep> getSteps() {
		return steps;
	}
	
	public void setSteps(Iterable<RecipeStep> steps) {
		this.steps = steps;
	}

}
