package utils;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class TerminalSingleton {
    private static Terminal terminal = null;

    private TerminalSingleton() {
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Problem with terminal creating");
        }
    }

    public static Terminal getInstance() {
        if(terminal == null) {
            new TerminalSingleton();
        }
        return terminal;
    }
}
