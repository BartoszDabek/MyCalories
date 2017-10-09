package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.mycalories.model.Meal;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.MealService;

import java.util.Iterator;

@Service
public class MealServiceImpl extends AbstractServiceImpl<Meal, Long> implements MealService {

    @Autowired
    public MealServiceImpl(JpaRepository<Meal, Long> repository) {
        super(repository);
    }

    @Override
    public Meal save(Meal meal) {
        meal.setNutritionalValues(new NutritionalValues(meal));
        return super.save(meal);
    }

    @Override
    public void delete(Meal meal) {

        Iterator<ProductMeal> iterator = meal.getProductMeals().iterator();
        while (iterator.hasNext()) {
            ProductMeal productMeal = iterator.next();
            iterator.remove();
        }
        super.delete(meal);
    }
}
