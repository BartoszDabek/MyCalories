package pl.mycalories.service;

import pl.mycalories.model.Opinion;

import java.util.Set;

public interface OpinionService extends AbstractService<Opinion, Long>{

    Set<Opinion> getAllOpinions(Long productId);
}
