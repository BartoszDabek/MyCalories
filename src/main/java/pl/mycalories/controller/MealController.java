package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.Meal;
import pl.mycalories.service.MealService;
import pl.mycalories.service.MealServiceError;

@RestController
@RequestMapping("/meal")
public class MealController extends AbstractController<Meal, MealService> {

    private MealServiceError mealServiceError;

    @Autowired
    public void setMealServiceError(MealServiceError mealServiceError) {
        this.mealServiceError = mealServiceError;
    }

    @Autowired
    public MealController(MealService service) {
        super(service);
    }

    @Override
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody Meal meal) {
        ErrorInformation errorInformation = mealServiceError.checkIfProductsAreModified(meal);

        if(errorInformation.getHttpStatus() != HttpStatus.OK) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getHttpStatus());
        }

        Meal createdMeal = service.save(meal);
        return new ResponseEntity<Meal>(createdMeal, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Meal meal) {
        ErrorInformation errorInformation = mealServiceError.checkIfProductsAreModified(meal);

        if(errorInformation.getHttpStatus() != HttpStatus.OK) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getHttpStatus());
        }

        meal.setId(id);
        meal = service.save(meal);
        return new ResponseEntity<Meal>(meal, HttpStatus.OK);
    }
}
