package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
