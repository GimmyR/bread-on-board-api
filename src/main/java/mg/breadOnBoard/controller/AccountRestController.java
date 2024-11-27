package mg.breadOnBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mg.breadOnBoard.service.AccountService;

@RestController
public class AccountRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/api/account/log-in")
	public ResponseEntity<String> logIn(@RequestParam String username, @RequestParam String password) {
		
		ResponseEntity<String> response = null;
		
		try {
		
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			response = new ResponseEntity<String>(accountService.generateJWT(username), HttpStatus.OK);
		
		} catch(AuthenticationException e) {
			
			response = new ResponseEntity<String>("Connexion impossible", HttpStatus.UNAUTHORIZED);
			
		} return response;
		
	}

}
