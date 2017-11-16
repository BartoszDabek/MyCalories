package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Product;

import javax.transaction.Transactional;

public interface ProductDao extends JpaRepository<Product, Long> {
    Product findByName(String name);

    @Transactional
    Long deleteByName(String name);
}
