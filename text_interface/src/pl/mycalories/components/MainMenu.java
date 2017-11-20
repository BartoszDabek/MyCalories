package pl.mycalories.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

public class MainMenu extends AbstractWindow {

    public MainMenu(MultiWindowTextGUI gui) {
        super(gui);

        Panel header = new Panel(new LinearLayout(Direction.HORIZONTAL));

        header.addComponent(new Button("Categories", () -> new Categories(gui, "Categories")));
        header.addComponent(new Button("Products", () -> new Products(gui, "Products")));
        header.addComponent(new EmptySpace(new TerminalSize(20,0)));
        if (Login.isLoggedIn()) {
            header.addComponent(new Button("Logout", () -> {
                Login.setLoggedOut();
                window.close();
                popupMessageDialog(gui, "You have been logged out");
                new MainMenu(gui);
            }));
        } else {
            header.addComponent(new Button("Login", () -> {
                window.close();
                new Login(gui);
            }));
        }
        header.addComponent(new Button("Exit", () -> System.exit(0)));

        window.setComponent(header);
        gui.addWindowAndWait(window);
    }

}
