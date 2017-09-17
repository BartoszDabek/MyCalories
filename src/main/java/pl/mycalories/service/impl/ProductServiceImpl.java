package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.ProductService;

@Service
public class ProductServiceImpl extends AbstractServiceImpl<Product, Long> implements ProductService {

    @Autowired
    public ProductServiceImpl(JpaRepository<Product, Long> repository) {
        super(repository);
    }

    @Override
    public void delete(Product product) {
        for(ProductMeal p: product.getProductMeals()) {
            p.setProduct(null);
        }
        super.delete(product);
    }
}
