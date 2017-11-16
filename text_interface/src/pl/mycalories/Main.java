package utils;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.mycalories.CalorieeCApplication;
import utils.MainMenu;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static ConfigurableApplicationContext ctx;

    public static void main(String[] args) throws IOException {
        ctx = new SpringApplicationBuilder(CalorieeCApplication.class)
                .headless(false).run(args);

        Terminal terminal = TerminalSingleton.getInstance();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        new MainMenu(gui);
    }
}
