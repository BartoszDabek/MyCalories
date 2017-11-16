package utils;

import com.googlecode.lanterna.gui2.*;

public class MainMenu {

    MultiWindowTextGUI gui;

    public MainMenu(MultiWindowTextGUI gui) {
        this.gui = gui;

        Panel header = new Panel(new LinearLayout(Direction.HORIZONTAL));
        final BasicWindow window = new BasicWindow();

        header.addComponent(new Button("Categories", () -> new Categories(gui)));
        header.addComponent(new Button("Products", () -> new Products(gui)));
        header.addComponent(new Button("Register", () -> new Register(gui)));
        header.addComponent(new Button("Exit", () -> exitApp()));

        window.setComponent(header);
        if(this.gui.getActiveWindow() == null) {
            gui.addWindowAndWait(window);
        }
    }

    private void exitApp() {
        try {
            this.gui.getScreen().stopScreen();
            System.exit(0);
        } catch (Exception e) {

        }
    }
}
