package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.mycalories.dao.DailyCaloriesDao;
import pl.mycalories.model.*;
import pl.mycalories.security.SecurityUtils;
import pl.mycalories.service.DailyCaloriesService;

import java.time.LocalDate;
import java.util.Date;

@Service
public class DailyCaloriesServiceImpl extends AbstractServiceImpl<DailyCalories, Long, DailyCaloriesDao>
        implements DailyCaloriesService {

    private DailyCalories dailyCalories;

    @Autowired
    public DailyCaloriesServiceImpl(DailyCaloriesDao repository) {
        super(repository);
    }

    @Override
    public DailyCalories save(DailyCalories dailyCalories) {
        this.dailyCalories = dailyCalories;
        setMealsNutritionalValues();
        dailyCalories.setNutritionalValues(new NutritionalValues(dailyCalories));

        return super.save(dailyCalories);
    }

    @Override
    public DailyCalories findByDate(User user, LocalDate date) {
        return repository.findByDate(user, date);
    }

    private void setMealsNutritionalValues() {
        for(Meal meal: dailyCalories.getMeals()) {
            meal.setNutritionalValues(new NutritionalValues(meal));
        }
    }
}
