package pl.mycalories.service;

import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.Meal;
import pl.mycalories.model.Product;

public interface ErrorService {
    ErrorInformation checkIfProductsAreModified(Meal meal);
}
