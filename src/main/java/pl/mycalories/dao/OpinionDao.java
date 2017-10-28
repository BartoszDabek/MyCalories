package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Opinion;

public interface OpinionDao extends JpaRepository<Opinion, Long> {

}
