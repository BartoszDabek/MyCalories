package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.error.MealErrors;
import pl.mycalories.model.Meal;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.MealServiceError;
import pl.mycalories.service.ProductService;

@Service
public class MealServiceErrorImpl implements MealServiceError {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ErrorInformation checkIfProductsAreModified(Meal meal) {
        for(ProductMeal p: meal.getProductMeals()) {
            Product actualProduct = p.getProduct();
            Product originalProduct = productService.findById(p.getProduct().getId());

            if(!actualProduct.equals(originalProduct)) {
                return new ErrorInformation(MealErrors.CONFLICT);
            }
        }
        return new ErrorInformation(MealErrors.OK);
    }
}
