import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import pl.mycalories.components.MainMenu;

import java.util.Arrays;

public class Register {


    public Register(MultiWindowTextGUI gui) {

            Panel register = new Panel();
            BasicWindow window = new BasicWindow();

            register.setLayoutManager(new GridLayout(2));

            register.addComponent(new Label("Forename"));
            register.addComponent(new TextBox());

            register.addComponent(new Label("Surname"));
            register.addComponent(new TextBox());

            register.addComponent(new EmptySpace(new TerminalSize(0, 0)));
            register.addComponent(new Button("Submit", () -> {
                    window.close();
                    new MainMenu(gui);
                }));

            window.setComponent(register);
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            window.setCloseWindowWithEscape(true);
            gui.addWindow(window);
    }
}
