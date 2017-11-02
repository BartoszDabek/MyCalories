package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.Meal;
import pl.mycalories.model.Opinion;
import pl.mycalories.model.Product;
import pl.mycalories.service.ErrorService;
import pl.mycalories.service.OpinionService;
import pl.mycalories.service.ProductService;

import java.util.Set;

@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController<Product, ProductService> {

    private ErrorService errorService;
    private ErrorInformation errorInformation;
    private OpinionService opinionService;

    @Autowired
    public ProductController(ProductService service) {
        super(service);
    }

    @Autowired
    public void setErrorService(ErrorService errorService) {
        this.errorService = errorService;
    }

    @Autowired
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody Product product) {
        if(errorOccurs(product)) {
            return new ResponseEntity<ErrorInformation>(errorInformation, errorInformation.getErrorStatus());
        }

        return super.create(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product obj) {
        return super.update(id, obj);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Product> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @GetMapping(value = "/{productId}/opinions")
    public @ResponseBody
    ResponseEntity<?> getOpinions(@PathVariable Long productId) {
        Set<Opinion> opinions = opinionService.getAllOpinions(productId);
        return new ResponseEntity<Set<Opinion>>(opinions, HttpStatus.OK);
    }

    private boolean errorOccurs(Product product) {
        errorInformation = errorService.productNameIsAlreadyTaken(product);
        if(errorInformation != null) {
            return true;
        }
        return false;
    }
}
