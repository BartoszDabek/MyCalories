package pl.mycalories.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.mycalories.CalorieeCApplication;
import pl.mycalories.model.Meal;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalorieeCApplication.class)
@WebAppConfiguration
@Transactional
public class MealServiceImplTest {
//TODO: hibernate nextval - wywalić pobieranie podczas testów
    @Autowired
    private MealService mealService;

    @Mock
    private ProductService productService;

    private Meal meal;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        meal = new Meal();
        meal.setName("Test meal");
    }

    @Test
    public void insert_meal(){
        String expectedName = "Test meal";

        mealService.save(meal);
        Meal dbMeal = mealService.findById(meal.getId());

        Assert.assertNotNull(dbMeal);
        Assert.assertEquals(expectedName, dbMeal.getName());
    }

    @Test
    public void nutritional_values_are_match_after_save() {
        NutritionalValues expectedValue = new NutritionalValues(1000, 100, 100, 100);
        Product product = new Product();
        product.setNutritionalValues(new NutritionalValues(100, 10, 10, 10));

        when(productService.findById(any(Long.class))).thenReturn(product);

        Set<ProductMeal> productMeals = new HashSet<>();
        ProductMeal productMeal = new ProductMeal();
        productMeal.setAmount(10);
        productMeal.setProduct(productService.findById(1L));
        productMeals.add(productMeal);
        meal.setProductMeals(productMeals);

        mealService.save(meal);

        Meal dbMeal = mealService.findById(meal.getId());
        Assert.assertNotNull(dbMeal);
        Assert.assertEquals(expectedValue, dbMeal.getNutritionalValues());
    }

    @Test
    public void update_meal(){
        String updatedName = "This is an updated meal";

        mealService.save(meal);

        Meal updatedMeal = mealService.findById(meal.getId());
        updatedMeal.setName(updatedName);
        mealService.save(updatedMeal);

        Meal dbMeal = mealService.findById(updatedMeal.getId());
        Assert.assertNotNull(dbMeal);
        Assert.assertEquals(updatedName, dbMeal.getName());
    }

    @Test
    public void delete_meal(){
        mealService.save(meal);

        Meal mealToDelete = mealService.findById(meal.getId());
        mealService.delete(mealToDelete);

        Meal dbMeal = mealService.findById(mealToDelete.getId());
        Assert.assertNull(dbMeal);
    }

}
