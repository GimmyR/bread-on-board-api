package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public Iterable<Account> findAll() {
		
		return accountRepository.findAll();
		
	}
	
	public Account findOneById(String id) throws AccountNotFoundException {
		
		Optional<Account> opt = accountRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new AccountNotFoundException();
		
	}
	
	public Account save(Account account) {
		
		return accountRepository.save(account);
		
	}
	
	public void delete(Account account) {
		
		accountRepository.delete(account);
		
	}

}
