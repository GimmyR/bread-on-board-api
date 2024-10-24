package mg.breadOnBoard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

}
