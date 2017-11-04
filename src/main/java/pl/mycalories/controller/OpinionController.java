package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.model.Category;
import pl.mycalories.model.Opinion;
import pl.mycalories.service.OpinionService;

import java.util.Date;

@RestController
@RequestMapping("/opinion")
public class OpinionController extends AbstractController <Opinion, OpinionService> {

    @Autowired
    public OpinionController(OpinionService service) {
        super(service);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody Opinion obj) {
        if (obj.getAddDate() == null){
            obj.setAddDate(new Date());
        }
        return super.create(obj);
    }

    @PreAuthorize("hasRole('ADMIN') or @permissionComponent.canDeleteOpinion( #id )")
    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Opinion> delete (@PathVariable Long id) {
        return super.delete(id);
    }

}
