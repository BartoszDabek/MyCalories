package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
}
