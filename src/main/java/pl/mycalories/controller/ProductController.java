package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycalories.model.Product;
import pl.mycalories.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController<Product, ProductService> {

    @Autowired
    public ProductController(ProductService service) {
        super(service);
    }
}
