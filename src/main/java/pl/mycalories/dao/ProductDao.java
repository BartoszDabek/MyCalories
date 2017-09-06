package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

}
