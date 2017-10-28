package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycalories.model.Opinion;
import pl.mycalories.service.OpinionService;

@RestController
@RequestMapping("/opinion")
public class OpinionController extends AbstractController <Opinion, OpinionService> {

    @Autowired
    public OpinionController(OpinionService service) {
        super(service);
    }



}
