package pl.mycalories.service;

import pl.mycalories.model.Product;

public interface ProductService extends AbstractService <Product, Long> {
    Product findByName(String name);
}
