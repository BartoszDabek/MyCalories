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
import pl.mycalories.dao.ProductDao;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.impl.ProductServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CalorieeCApplication.class)
public class ProductServiceImplTest {

    private ProductService productService;
    @Mock
    private ProductDao productDaoMock;
    @Mock
    private Product productMock;
    @Mock
    private List<Product> productsMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productDaoMock);
    }

    @Test
    public void should_Return_List_Of_Products_When_getAll_Is_Called() {
        when(productDaoMock.findAll()).thenReturn(productsMock);
        List<Product> retriviedProducts = productService.getAll();
        Assert.assertThat(retriviedProducts, is(equalTo(productsMock)));
    }

    @Test
    public void should_Return_Product_When_findById_Is_Called() {
        when(productDaoMock.findById(5L)).thenReturn(Optional.of(productMock));
        Product retriviedProduct = productService.findById(5L);
        Assert.assertThat(retriviedProduct, is(equalTo(productMock)));
    }

    @Test
    public void should_Return_Product_When_Save_Is_Called() {
        when(productDaoMock.save(productMock)).thenReturn(productMock);
        Product savedProduct = productService.save(productMock);
        Assert.assertThat(savedProduct, is(equalTo(productMock)));
    }

    @Test
    public void should_Call_Delete_Method_On_productDaoMock_When_Delete_Is_Called() {
        doNothing().when(productDaoMock).delete(productMock);
        productService.delete(productMock);
        verify(productDaoMock, times(1)).delete(productMock);
    }

    @Test
    public void should_Set_Null_On_ProductMeal_When_Delete_Is_Called() {
        ProductMeal productMeal = new ProductMeal();
        Product product = new Product();
        product.setProductMeals(new HashSet<>());
        product.getProductMeals().add(productMeal);
        productMeal.setProduct(product);

        when(productDaoMock.save(product)).thenReturn(product);
        Product newProduct = productService.save(product);

        productService.delete(newProduct);
        Assert.assertNull(productMeal.getProduct());
    }

    @Test
    public void should_Set_Null_On_ProductMeals_When_Delete_Is_Called() {
        ProductMeal productMeal1 = new ProductMeal();
        ProductMeal productMeal2 = new ProductMeal();
        productMeal1.setId(1L);
        productMeal2.setId(2L);

        Product product = new Product();
        product.setProductMeals(new HashSet<>());
        product.getProductMeals().add(productMeal1);
        product.getProductMeals().add(productMeal2);

        productMeal1.setProduct(product);
        productMeal2.setProduct(product);

        when(productDaoMock.save(product)).thenReturn(product);
        Product newProduct = productService.save(product);

        productService.delete(newProduct);
        verify(productDaoMock, times(1)).delete(newProduct);
        Assert.assertNull(productMeal1.getProduct());
        Assert.assertNull(productMeal2.getProduct());
    }

    @Test
    public void should_Not_Set_Null_On_ProductMeal_When_Delete_Is_Called() {
        ProductMeal productMeal = new ProductMeal();
        Product product = new Product();
        product.setProductMeals(new HashSet<>());
        product.getProductMeals().add(new ProductMeal());
        productMeal.setProduct(productMock);

        when(productDaoMock.save(product)).thenReturn(product);
        Product newProduct = productService.save(product);


        productService.delete(newProduct);
        verify(productDaoMock, times(1)).delete(newProduct);
        Assert.assertNotNull(productMeal.getProduct());
    }
}
