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

import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Session;
import mg.breadOnBoard.service.AccountNotFoundException;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.SessionNotFoundException;
import mg.breadOnBoard.service.SessionService;

@RestController
public class AccountRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private SessionService sessionService;
	
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
	
	@PostMapping("/api/account/auth")
	public ResponseEntity<String> auth(@RequestParam String token) {
		
		ResponseEntity<String> response = null;
		
		try {
			
			Session session = sessionService.findById(token);
			Account account = accountService.findById(session.getAccountId());
			response = new ResponseEntity<String>(account.getUsername(), HttpStatus.OK);
			
		} catch (SessionNotFoundException | AccountNotFoundException e) {

			response = new ResponseEntity<String>("Session introuvable !", HttpStatus.NOT_FOUND);
			
		} return response;
		
	}
	
	@PostMapping("/api/account/log-out")
	public ResponseEntity<String> logOut(@RequestParam String token) {
		
		ResponseEntity<String> response = null;
		
		try {
			
			Session session = sessionService.findById(token);
			sessionService.delete(session);
			response = new ResponseEntity<String>("Vous êtes déconnecté !", HttpStatus.OK);
			
		} catch (SessionNotFoundException e) {

			response = new ResponseEntity<String>("Session introuvable !", HttpStatus.NOT_FOUND);
			
		} return response;
		
	}

}
