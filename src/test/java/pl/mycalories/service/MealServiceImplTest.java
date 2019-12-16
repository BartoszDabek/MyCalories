package pl.mycalories.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.mycalories.CalorieeCApplication;
import pl.mycalories.dao.MealDao;
import pl.mycalories.model.Meal;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.impl.MealServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CalorieeCApplication.class)
public class MealServiceImplTest {

    private MealService mealService;
    @Mock
    private MealDao mealDao;
    @Mock
    private Meal meal;
    @Mock
    private List<Meal> meals;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mealService = new MealServiceImpl(mealDao);
    }

    @Test
    public void should_return_list_of_meals_when_getAll_is_called() {
        when(mealDao.findAll()).thenReturn(meals);
        List<Meal> retriviedMeals = mealService.getAll();
        Assert.assertThat(retriviedMeals, is(equalTo(meals)));
    }

    @Test
    public void should_return_meal_when_find_by_id_is_called() {
        when(mealDao.findById(5L)).thenReturn(Optional.of(meal));
        Meal retrievedMeal = mealService.findById(5L);
        Assert.assertThat(retrievedMeal, is(equalTo(meal)));
    }

    @Test
    public void should_return_meal_when_save_is_called(){
        when(mealDao.save(meal)).thenReturn(meal);
        Meal savedMeal = mealService.save(meal);
        Assert.assertThat(savedMeal, is(equalTo(meal)));
    }

    @Test
    public void should_call_delete_method_of_mealDao_when_delete_is_called(){
        doNothing().when(mealDao).delete(meal);
        mealService.delete(meal);
        verify(mealDao, times(1)).delete(meal);
    }

    @Test
    public void should_set_NutritionalValues_of_meal_when_save_is_called() {
        NutritionalValues expectedNutritionalValues = new NutritionalValues(1000, 100, 100, 100);

        Product product = new Product();
        product.setNutritionalValues(new NutritionalValues(100, 10, 10, 10));

        ProductMeal productMeal = new ProductMeal();
        productMeal.setAmount(10);
        productMeal.setProduct(product);

        Meal meal = new Meal();
        meal.setProductMeals(new HashSet<>());
        meal.getProductMeals().add(productMeal);

        when(mealDao.save(meal)).thenReturn(meal);
        Meal savedMeal = mealService.save(meal);
        Assert.assertEquals(expectedNutritionalValues, savedMeal.getNutritionalValues());
    }

    @Test
    public void should_remove_related_ProductMeals_when_delete_is_salled() {
        Meal meal = new Meal();
        ProductMeal productMeal = new ProductMeal();
        ProductMeal productMeal1 = new ProductMeal();
        productMeal.setId(1L);
        productMeal1.setId(2L);
        meal.setProductMeals(new HashSet<>());
        meal.getProductMeals().add(productMeal);
        meal.getProductMeals().add(productMeal1);

        mealService.delete(meal);

        Assert.assertTrue(meal.getProductMeals().isEmpty());
    }

}
