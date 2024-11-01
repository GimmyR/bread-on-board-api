package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Account findById(String id) throws AccountNotFoundException {
		
		Optional<Account> opt = accountRepository.findById(id);
		
		if(opt.isEmpty())
			throw new AccountNotFoundException();
		
		else return opt.get();
		
	}
	
	public Account findByUsernameAndPassword(String username, String password) {
		
		TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.username = :username AND a.password = :password", Account.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		
		return query.getSingleResult();
		
	}
	
	public Account findByIdAndPassword(String id, String password) {
		
		TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.id = :id AND a.password = :password", Account.class);
		query.setParameter("id", id);
		query.setParameter("password", password);
		
		return query.getSingleResult();
		
	}
	
	public Account save(Account account) {
		
		return accountRepository.save(account);
		
	}
	
	public void delete(Account account) {
		
		accountRepository.delete(account);
		
	}

}
