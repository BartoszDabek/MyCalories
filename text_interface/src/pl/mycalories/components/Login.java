package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import pl.mycalories.Main;
import pl.mycalories.model.Role;
import pl.mycalories.model.User;
import pl.mycalories.service.UserService;
import pl.mycalories.utils.Helper;

import java.util.Arrays;

public class Login {

    private UserService userService = Main.ctx.getBean(UserService.class);
    private static boolean userLoggedIn = false;
    private TextBox username;
    private TextBox password;
    private static User user;

    public Login(MultiWindowTextGUI gui) {
        Panel register = new Panel(new GridLayout(2));
        BasicWindow window = new BasicWindow();

        username = new TextBox();
        password = new TextBox().setMask('*');

        register.addComponent(new Label("Login"));
        register.addComponent(username);

        register.addComponent(new Label("Password"));
        register.addComponent(password);

        register.addComponent(new Button("Cancel", () -> {
            window.close();
            new MainMenu(gui);
        }));
        register.addComponent(new Button("Submit", () -> {
            try {
                user = userService.checkUserAuthentication(username.getText(), password.getText());
                userLoggedIn = true;
                window.close();
                new MainMenu(gui);
            } catch (Exception e) {
                Helper.exceptionMessageDialog(gui, e.getMessage());
            }
        }));

        window.setComponent(register);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        gui.addWindowAndWait(window);
    }

    public static boolean isLoggedIn() {
        return userLoggedIn;
    }

    public static User getCurrentUser() {
        return user;
    }

    public static void setLoggedOut() {
        user = null;
        userLoggedIn = false;
    }

    public static boolean hasAdminRights() {
        for(Role r: user.getRoles()) {
            if(r.getRoleName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
