package mg.breadOnBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Account {
	
	// ATTRIBUTES :
	
	public static final String PREFIX = "A";
	
	public static final int LENGTH_ID = 4; // Minus 1 because it's without the prefix
	
	@Id
	private String id;
	
	private String username;
	
	@Column(name = "mail_address")
	private String mailAddress;
	
	private String password;
	
	// GETTERS AND SETTERS :
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getMailAddress() {
		return mailAddress;
	}
	
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
