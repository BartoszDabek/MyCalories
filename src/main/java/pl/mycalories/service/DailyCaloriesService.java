package pl.mycalories.service;

import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.User;

import java.time.LocalDate;

public interface DailyCaloriesService extends AbstractService<DailyCalories, Long> {

    DailyCalories findByDate(User user, LocalDate date);
}
