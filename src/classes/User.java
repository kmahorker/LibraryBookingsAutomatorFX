package classes;

public class User {
	
	private String username;
	private String passwordSalt;
	public User(String username, String passwordSalt) {
		super();
		this.username = username;
		this.passwordSalt = passwordSalt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	
	
	

}
