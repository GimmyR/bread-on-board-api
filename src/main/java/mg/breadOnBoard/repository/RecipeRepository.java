package mg.breadOnBoard.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Recipe;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {
	
	Iterable<Recipe> findByAccountId(String accountId, Sort sort);
	
	Iterable<Recipe> findByTitleLikeOrIngredientsLike(String title, String ingredients, Sort sort);
	
	Recipe findOneByIdAndAccountId(String id, String accountId);

}
