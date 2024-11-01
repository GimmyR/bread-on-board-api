package mg.breadOnBoard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {

}
