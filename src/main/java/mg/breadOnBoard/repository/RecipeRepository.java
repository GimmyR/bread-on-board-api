package mg.breadOnBoard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
