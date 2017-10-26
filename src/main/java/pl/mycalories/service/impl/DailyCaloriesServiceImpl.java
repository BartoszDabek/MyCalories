package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.mycalories.dao.DailyCaloriesDao;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Meal;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.service.DailyCaloriesService;

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
        setNewDate();
        dailyCalories.setNutritionalValues(new NutritionalValues(dailyCalories));

        return super.save(dailyCalories);
    }
// TODO: a co jak ktoś z palca wbije date wysylajac JSON'a ?? !!
    private void setNewDate() {
//        if(dailyCalories.getDate() == null) {
//            dailyCalories.setDate(new Date());
//        }
    }

    private void setMealsNutritionalValues() {
        for(Meal meal: dailyCalories.getMeals()) {
            meal.setNutritionalValues(new NutritionalValues(meal));
        }
    }
}
