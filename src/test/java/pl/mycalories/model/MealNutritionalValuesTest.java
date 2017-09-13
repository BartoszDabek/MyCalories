package pl.mycalories.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.mycalories.CalorieeCApplication;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalorieeCApplication.class)
@WebAppConfiguration
public class MealNutritionalValuesTest {

    private Meal meal;

    @Before
    public void setUp() {
        meal = new Meal();
        meal.setId(1L);
        meal.setName("test meal");
        meal.setProductMeals(addProductMeal());
        meal.setNutritionalValues(new NutritionalValues(meal));
    }

    @Test
    public void sumMacroTest() {
        Assert.assertEquals(1250, meal.getNutritionalValues().getCalories());
        Assert.assertEquals(250, meal.getNutritionalValues().getCarbs());
        Assert.assertEquals(500, meal.getNutritionalValues().getProteins());
        Assert.assertEquals(750, meal.getNutritionalValues().getFats());
    }


    private Set<ProductMeal> addProductMeal() {
        ProductMeal firstProduct = getProductMeal(10);
        ProductMeal secondProduct = getProductMeal(15);

        Set<ProductMeal> productMeals = new HashSet<>();
        productMeals.add(firstProduct);
        productMeals.add(secondProduct);

        return productMeals;
    }

    private ProductMeal getProductMeal(int quantity) {
        ProductMeal productMeal = new ProductMeal();
        productMeal.setAmount(quantity);
        productMeal.setProduct(getProduct());

        return productMeal;
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("test product");
        product.setNutritionalValues(new NutritionalValues(50, 20, 30, 10));

        return product;
    }
}
