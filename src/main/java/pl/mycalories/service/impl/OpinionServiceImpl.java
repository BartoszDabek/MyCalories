package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycalories.dao.OpinionDao;
import pl.mycalories.model.Opinion;
import pl.mycalories.service.OpinionService;

@Service
public class OpinionServiceImpl extends AbstractServiceImpl<Opinion, Long, OpinionDao>
        implements OpinionService {

    @Autowired
    public OpinionServiceImpl(OpinionDao repository) {
        super(repository);
    }



}
