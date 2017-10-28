package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<Category, CategoryService> {

    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }

    @PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
    @Override
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody Category obj) {
        return super.create(obj);
    }

    @PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Category obj) {
        return super.update(id, obj);
    }


    @PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Category> delete (@PathVariable Long id) {
        return super.delete(id);
    }

}
