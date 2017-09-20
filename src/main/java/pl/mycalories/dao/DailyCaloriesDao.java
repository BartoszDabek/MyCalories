package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.DailyCalories;

public interface DailyCaloriesDao extends JpaRepository<DailyCalories, Long> {

}
