package pl.mycalories.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.mycalories.common.NameConverter;
import pl.mycalories.dao.CategoryDao;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

import java.util.UnknownFormatConversionException;

@Service
public class CategoryServiceImpl extends AbstractServiceImpl<Category, Long, CategoryDao>
        implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryDao repository) {
        super(repository);
    }

    @Override
    public Category save(Category category) {
        NameConverter nameConverter = new NameConverter(category);
        try {
            nameConverter.convertName();
        } catch (UnknownFormatConversionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return super.save(category);
    }

}
