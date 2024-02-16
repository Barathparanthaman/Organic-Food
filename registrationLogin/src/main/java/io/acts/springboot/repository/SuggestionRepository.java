package io.acts.springboot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.acts.springboot.entity.Suggestion;

@Repository

public interface SuggestionRepository extends JpaRepository<Suggestion,Long>{

	void save(String string);
	List<Suggestion> findByType(String type);

}
