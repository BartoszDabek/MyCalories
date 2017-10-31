package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.Meal;
import pl.mycalories.service.MealService;
import pl.mycalories.service.ErrorService;

@RestController
@RequestMapping("/meal")
public class MealController extends AbstractController<Meal, MealService> {
//TODO: ZMIENIC ENDPOINT'Y -- DOSTEP DO MEAL PRZEZ /dailyCalories/{id}/meal ??  USUNĄC post/update/delete bezposrednio na mealu
    // TODO: lub przez /user/{userName}/dailyCalories/{id}/meal    ??
    private ErrorService errorService;
    private ErrorInformation errorInformation;

    @Autowired
    public void setErrorService(ErrorService errorService) {
        this.errorService = errorService;
    }

    @Autowired
    public MealController(MealService service) {
        super(service);
    }

    @Override
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody Meal meal) {
        if(errorOccurs(meal)) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getErrorStatus());
        }

        return super.create(meal);
    }

    @Override
    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Meal meal) {
        if(errorOccurs(meal)) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getErrorStatus());
        }

        return super.update(id, meal);
    }

    private boolean errorOccurs(Meal meal) {
        errorInformation = errorService.checkIfProductsAreModified(meal);
        if(errorInformation != null) {
            return true;
        }
        return false;
    }
}
