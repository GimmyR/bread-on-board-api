package mg.breadOnBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Account;




@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	
	Account findOneByUsernameAndPassword(String username, String password);
	
	Account findOneByIdAndPassword(String id, String password);
	
	Account findOneByUsername(String username);

}
