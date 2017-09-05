package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<Category, CategoryService> {

    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }
}
