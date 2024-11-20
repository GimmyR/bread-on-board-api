package mg.breadOnBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> findAll() {
		
		return accountRepository.findAll();
		
	}
	
	public Account findById(String id) throws AccountNotFoundException {
		
		Optional<Account> opt = accountRepository.findById(id);
		
		if(opt.isEmpty())
			throw new AccountNotFoundException();
		
		else return opt.get();
		
	}
	
	public Account findByUsernameAndPassword(String username, String password) {
		
		Account account = accountRepository.findOneByUsernameAndPassword(username, password);
		
		if(account == null)
			throw new NoResultException();
		
		return account;
		
	}
	
	public Account findByIdAndPassword(String id, String password) {
		
		Account account = accountRepository.findOneByIdAndPassword(id, password);
		
		if(account == null)
			throw new NoResultException();
		
		return account;
		
	}
	
	public Account save(Account account) {
		
		return accountRepository.save(account);
		
	}
	
	public void delete(Account account) {
		
		accountRepository.delete(account);
		
	}

}
