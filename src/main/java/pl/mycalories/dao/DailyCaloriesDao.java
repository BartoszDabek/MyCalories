package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Day;
import pl.mycalories.model.User;

import java.time.LocalDate;
import java.util.Set;

public interface DailyCaloriesDao extends JpaRepository<DailyCalories, Long> {

    @Query("SELECT dc FROM DailyCalories dc JOIN dc.day d JOIN d.user u WHERE u = :user AND d.date = :date")
    DailyCalories findByDate(@Param("user") User user, @Param("date") LocalDate date);
}
