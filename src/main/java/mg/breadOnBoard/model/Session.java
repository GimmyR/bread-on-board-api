package mg.breadOnBoard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_session")
public class Session {
	
	public static final int LENGTH_ID = 16;
	
	// ATTRIBUTES :
	
	@Id
	private String id;
	
	private String accountId;
	
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

}
