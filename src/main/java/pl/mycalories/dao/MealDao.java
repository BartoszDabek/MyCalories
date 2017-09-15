package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Meal;

public interface MealDao extends JpaRepository<Meal, Long> {
}
