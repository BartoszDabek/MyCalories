package pl.mycalories.error;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.mycalories.CalorieeCApplication;
import pl.mycalories.model.Meal;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.ProductService;
import pl.mycalories.service.impl.ErrorServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = CalorieeCApplication.class)
public class ErrorServiceTest {

    @InjectMocks
    private ErrorServiceImpl errorService;
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
    public void should_return_null_because_product_is_not_modified() {
        Mockito.when(productService.findById(any(Long.class))).thenReturn(originalProduct);
        ErrorInformation errorInformation = errorService.checkIfProductsAreModified(meal);
        Assert.assertEquals(null, errorInformation);
    }

    @Test
    public void should_return_status_CONFLICT_409_because_product_macro_is_modified() {
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        testedProduct.setNutritionalValues(new NutritionalValues(100, 30, 30, 10));

        Mockito.when(productService.findById(any(Long.class))).thenReturn(testedProduct);
        ErrorInformation errorInformation = errorService.checkIfProductsAreModified(meal);
        verify(productService, times(1)).findById(any(Long.class));
        Assert.assertEquals(expectedStatus, errorInformation.getErrorStatus());
    }

    @Test
    public void should_return_status_CONFLICT_409_because_product_name_is_modified() {
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        testedProduct.setName("New product name");

        Mockito.when(productService.findById(any(Long.class))).thenReturn(testedProduct);
        ErrorInformation errorInformation = errorService.checkIfProductsAreModified(meal);
        verify(productService, times(1)).findById(any(Long.class));
        Assert.assertEquals(expectedStatus, errorInformation.getErrorStatus());
    }

    @Test
    public void should_return_status_CONFLICT_409_because_product_macro_is_deleted() {
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        testedProduct.setNutritionalValues(null);

        Mockito.when(productService.findById(any(Long.class))).thenReturn(testedProduct);
        ErrorInformation errorInformation = errorService.checkIfProductsAreModified(meal);
        verify(productService, times(1)).findById(any(Long.class));
        Assert.assertEquals(expectedStatus, errorInformation.getErrorStatus());
    }

}
