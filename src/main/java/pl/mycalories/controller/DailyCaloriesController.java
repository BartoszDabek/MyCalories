package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Meal;
import pl.mycalories.service.DailyCaloriesService;
import pl.mycalories.service.ErrorService;

import java.util.Date;

@RestController
@RequestMapping("/dailyCalories")
public class DailyCaloriesController extends AbstractController<DailyCalories, DailyCaloriesService> {

    private ErrorInformation errorInformation;
    private ErrorService errorService;

    @Autowired
    public void setErrorService(ErrorService errorService) {
        this.errorService = errorService;
    }

    @Autowired
    public DailyCaloriesController(DailyCaloriesService service) {
        super(service);
    }

    @Override
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody DailyCalories dailyCalories) {
        if(errorOccurs(dailyCalories)) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getHttpStatus());
        }

        return super.create(dailyCalories);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DailyCalories dailyCalories) {
        if(errorOccurs(dailyCalories)) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getHttpStatus());
        }

        return super.update(id, dailyCalories);
    }

    private boolean errorOccurs(DailyCalories dailyCalories) {
        for(Meal meal: dailyCalories.getMeals()) {
            errorInformation = errorService.checkIfProductsAreModified(meal);
            if(errorInformation.getHttpStatus() != HttpStatus.OK) {
                return true;
            }
        }
        return false;
    }
}
