package mg.breadOnBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Recipe {
	
	public static final String PREFIX = "R";
	
	public static final int LENGTH_ID = 5;
	
	// ATTRIBUTES :
	
	@Id
	private String id;
	
	@Column(name = "account_id")
	private String accountId;
	
	private String title;
	
	private String image;
	
	private String ingredients;
	
	// GETTERS AND SETTERS :
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

}
