package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import pl.mycalories.utils.Helper;

public class MainMenu {

    MultiWindowTextGUI gui;

    public MainMenu(MultiWindowTextGUI gui) {
        this.gui = gui;

        Panel header = new Panel(new LinearLayout(Direction.HORIZONTAL));
        final BasicWindow window = new BasicWindow();

        header.addComponent(new Button("Categories", () -> new Categories(gui)));
        header.addComponent(new Button("Products", () -> new Products(gui)));
        if (Login.isLoggedIn() == true) {
            header.addComponent(new Button("Logout", () -> {
                Login.setLoggedOut();
                window.close();
                Helper.exceptionMessageDialog(gui, "You have been logged out");
                new MainMenu(gui);
            }));
        } else {
            header.addComponent(new Button("Login", () -> {
                window.close();
                new Login(gui);
            }));
        }
        header.addComponent(new Button("Exit", () -> exitApp()));

        window.setComponent(header);
        gui.addWindowAndWait(window);
    }

    private void exitApp() {
        try {
            this.gui.getScreen().stopScreen();
            System.exit(0);
        } catch (Exception e) {

        }
    }
}
