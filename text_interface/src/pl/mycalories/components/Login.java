package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import pl.mycalories.Main;
import pl.mycalories.model.Role;
import pl.mycalories.model.User;
import pl.mycalories.service.UserService;

import java.util.Arrays;

public class Login extends AbstractWindow {

    private static User user;

    private UserService userService = Main.ctx.getBean(UserService.class);

    private Panel panelBox;
    private TextBox username;
    private TextBox password;

    public Login(MultiWindowTextGUI gui) {
        super(gui);
        init();

        panelBox.addComponent(new Label("Login"));
        panelBox.addComponent(username);

        panelBox.addComponent(new Label("Password"));
        panelBox.addComponent(password);

        panelBox.addComponent(new Button("Cancel", () -> {
            window.close();
            new MainMenu(gui);
        }));
        panelBox.addComponent(new Button("Submit", () -> login()));

        window.setComponent(panelBox);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        gui.addWindowAndWait(window);
    }

    private void init() {
        panelBox = new Panel(new GridLayout(2));

        username = new TextBox();
        password = new TextBox().setMask('*');
    }

    private void login() {
        try {
            user = userService.checkUserAuthentication(username.getText(), password.getText());
            window.close();
            new MainMenu(gui);
        } catch (Exception e) {
            popupMessageDialog(gui, e.getMessage());
        }
    }

    public static boolean isLoggedIn() {
        return user != null;
    }

    public static User getCurrentUser() {
        return user;
    }

    public static void setLoggedOut() {
        user = null;
    }

    public static boolean hasAdminRights() {
        for (Role r : user.getRoles()) {
            if (r.getRoleName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
