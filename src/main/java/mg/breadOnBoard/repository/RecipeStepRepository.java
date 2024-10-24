package mg.breadOnBoard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.RecipeStep;

@Repository
public interface RecipeStepRepository extends CrudRepository<RecipeStep, String> {

}
