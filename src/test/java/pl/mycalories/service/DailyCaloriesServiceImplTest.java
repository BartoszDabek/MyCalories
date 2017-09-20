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
import pl.mycalories.dao.DailyCaloriesDao;
import pl.mycalories.model.*;
import pl.mycalories.service.impl.DailyCaloriesServiceImpl;

import java.util.Date;
import java.util.HashSet;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalorieeCApplication.class)
@WebAppConfiguration
public class DailyCaloriesServiceImplTest {

    private DailyCaloriesService dailyCaloriesService;
    @Mock
    private DailyCaloriesDao dailyCaloriesDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        dailyCaloriesService = new DailyCaloriesServiceImpl(dailyCaloriesDao);
    }

    @Test
    public void should_Set_NutritionalValues_When_Save_Is_Called() {
        NutritionalValues expectedNutritionalValues = new NutritionalValues(2000, 200, 200, 200);
        int expectedCalories = 2000;

        DailyCalories dailyCalories = new DailyCalories();
        dailyCalories.setMeals(new HashSet<>());
        dailyCalories.getMeals().add(addNewMeal(1L));
        dailyCalories.getMeals().add(addNewMeal(2L));

        when(dailyCaloriesDao.save(dailyCalories)).thenReturn(dailyCalories);
        DailyCalories savedDailyCalories = dailyCaloriesService.save(dailyCalories);
        Assert.assertEquals(expectedNutritionalValues, savedDailyCalories.getNutritionalValues());
        Assert.assertEquals(expectedCalories, savedDailyCalories.getNutritionalValues().getCalories());
    }

    @Test
    public void should_Set_Meal_NutritionalValues_When_Save_Is_Called() {
        NutritionalValues expectedNutritionalValues = new NutritionalValues(1000, 100, 100, 100);
        int expectedCalories = 1000;

        DailyCalories dailyCalories = new DailyCalories();
        dailyCalories.setMeals(new HashSet<>());
        dailyCalories.getMeals().add(addNewMeal(1L));

        when(dailyCaloriesDao.save(dailyCalories)).thenReturn(dailyCalories);
        DailyCalories savedDailyCalories = dailyCaloriesService.save(dailyCalories);

        for(Meal meal: savedDailyCalories.getMeals()){
            Assert.assertEquals(expectedNutritionalValues, meal.getNutritionalValues());
            Assert.assertEquals(expectedCalories, meal.getNutritionalValues().getCalories());
        }
    }

    @Test
    public void should_Set_newDate_When_Save_Is_Called() {
        DailyCalories dailyCalories = new DailyCalories();

        when(dailyCaloriesDao.save(dailyCalories)).thenReturn(dailyCalories);
        DailyCalories savedDailyCalories = dailyCaloriesService.save(dailyCalories);
        Assert.assertNotNull(savedDailyCalories.getDate());
    }

    @Test
    public void should_Not_Set_newDate_When_Save_Is_Called_Because_Its_Update_Request() {
        long timeInMiliSecondsTo2015_12_31 = 1451516400000L;
        Date expectedDate = new Date(timeInMiliSecondsTo2015_12_31);
        DailyCalories dailyCalories = new DailyCalories();
        dailyCalories.setDate(expectedDate);

        when(dailyCaloriesDao.save(dailyCalories)).thenReturn(dailyCalories);
        DailyCalories savedDailyCalories = dailyCaloriesService.save(dailyCalories);
        Assert.assertEquals(expectedDate, savedDailyCalories.getDate());
    }

    private Meal addNewMeal(Long id) {
        Meal meal = new Meal();
        meal.setId(id);
        meal.setProductMeals(new HashSet<>());
        meal.getProductMeals().add(addNewProductMeal());
        return meal;
    }

    private ProductMeal addNewProductMeal() {
        ProductMeal productMeal = new ProductMeal();
        productMeal.setAmount(10);
        productMeal.setProduct(addNewProduct());
        return productMeal;
    }

    private Product addNewProduct() {
        Product product = new Product();
        product.setNutritionalValues(new NutritionalValues(100, 10, 10, 10));
        return product;
    }

}
