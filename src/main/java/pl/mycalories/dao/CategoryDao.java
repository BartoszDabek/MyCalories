package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Category;

import javax.transaction.Transactional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Transactional
    Long deleteByName(String name);
}
