package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

@Service
public class CategoryServiceImpl extends AbstractServiceImpl<Category, Long> implements CategoryService {

    @Autowired
    public CategoryServiceImpl(JpaRepository<Category, Long> repository) {
        super(repository);
    }


}
