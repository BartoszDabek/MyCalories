package pl.mycalories.error;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.mycalories.CalorieeCApplication;
import pl.mycalories.dao.ProductDao;
import pl.mycalories.model.Meal;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.MealServiceError;
import pl.mycalories.service.ProductService;
import pl.mycalories.service.impl.MealServiceErrorImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalorieeCApplication.class)
@WebAppConfiguration
public class MealServiceErrorTest {

    @InjectMocks
    private MealServiceErrorImpl mealServiceErrorMock;
    private Product originalProduct;
    private Product testedProduct;
    private Meal meal;

    @Mock
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        originalProduct = new Product();
        originalProduct.setId(1L);
        originalProduct.setName("Original product");
        originalProduct.setNutritionalValues(new NutritionalValues(50, 20, 30, 10));

        testedProduct = new Product();
        testedProduct.setId(1L);
        testedProduct.setName("Original product");
        testedProduct.setNutritionalValues(new NutritionalValues(50, 20, 30, 10));

        meal = new Meal();
        meal.setName("Test Meal");
        ProductMeal productMeal = new ProductMeal();
        productMeal.setAmount(30);
        productMeal.setProduct(originalProduct);
        meal.getProductMeals().add(productMeal);
    }

    @Test
    public void should_return_status_OK_200_because_product_is_not_modified() {
        HttpStatus expectedStatus = HttpStatus.OK;
        Mockito.when(productService.findById(any(Long.class))).thenReturn(originalProduct);
        ErrorInformation errorInformation = mealServiceErrorMock.checkIfProductsAreModified(meal);
        Assert.assertEquals(expectedStatus, errorInformation.getHttpStatus());
    }

    @Test
    public void should_return_status_CONFLICT_409_because_product_macro_is_modified() {
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        testedProduct.setNutritionalValues(new NutritionalValues(100, 30, 30, 10));

        Mockito.when(productService.findById(any(Long.class))).thenReturn(testedProduct);
        ErrorInformation errorInformation = mealServiceErrorMock.checkIfProductsAreModified(meal);
        verify(productService, times(1)).findById(any(Long.class));
        Assert.assertEquals(expectedStatus, errorInformation.getHttpStatus());
    }

    @Test
    public void should_return_status_CONFLICT_409_because_product_name_is_modified() {
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        testedProduct.setName("New product name");

        Mockito.when(productService.findById(any(Long.class))).thenReturn(testedProduct);
        ErrorInformation errorInformation = mealServiceErrorMock.checkIfProductsAreModified(meal);
        verify(productService, times(1)).findById(any(Long.class));
        Assert.assertEquals(expectedStatus, errorInformation.getHttpStatus());
    }

    @Test
    public void should_return_status_CONFLICT_409_because_product_macro_is_deleted() {
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        testedProduct.setNutritionalValues(null);

        Mockito.when(productService.findById(any(Long.class))).thenReturn(testedProduct);
        ErrorInformation errorInformation = mealServiceErrorMock.checkIfProductsAreModified(meal);
        verify(productService, times(1)).findById(any(Long.class));
        Assert.assertEquals(expectedStatus, errorInformation.getHttpStatus());
    }

}
