package pl.mycalories.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mycalories.model.Opinion;
import pl.mycalories.model.User;
import pl.mycalories.security.SecurityUtils;
import pl.mycalories.service.OpinionService;

@Component
public class PermissionComponent {

    private OpinionService opinionService;

    @Autowired
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService= opinionService;
    }

    public PermissionComponent() {
    }

    public boolean canDeleteOpinion(Long id) {
        Opinion opinion = opinionService.findById(id);
        User user = SecurityUtils.getCurrentUser();
        if(user != null && opinion.getUser().getUsername().equals(user.getUsername())) {
            return true;
        }
        return false;
    }
}
