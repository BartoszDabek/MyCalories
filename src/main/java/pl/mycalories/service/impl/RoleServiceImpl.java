package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycalories.dao.RoleDao;
import pl.mycalories.model.Role;
import pl.mycalories.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, Long, RoleDao>
        implements RoleService{

    @Autowired
    public RoleServiceImpl(RoleDao repository) {
        super(repository);
    }
}
