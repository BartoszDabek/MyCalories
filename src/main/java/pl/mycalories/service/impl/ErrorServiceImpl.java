package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.Meal;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.ErrorService;
import pl.mycalories.service.ProductService;

@Service
public class ErrorServiceImpl implements ErrorService {

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

            if(actualProduct.equals(originalProduct)) {
                return null;
            }
        }
        return new ErrorInformation(HttpStatus.CONFLICT, "You are trying to modify NO-MODIFIER-FIELDS");
    }

    @Override
    public ErrorInformation productNameIsAlreadyTaken(Product product) {
        Product productFound = productService.findByName(product.getName());

        if(productFound == null) {
            return null;
        }
        return new ErrorInformation(HttpStatus.CONFLICT, "Product with that name already exists!");
    }
}
