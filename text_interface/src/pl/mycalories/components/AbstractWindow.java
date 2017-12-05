package pl.mycalories.components;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public abstract class AbstractWindow {

    protected MultiWindowTextGUI gui;
    protected BasicWindow window;

    public AbstractWindow(MultiWindowTextGUI gui) {
        this.gui = gui;
        this.window = new BasicWindow();
    }

    public AbstractWindow(MultiWindowTextGUI gui, String title) {
        this.gui = gui;
        this.window = new BasicWindow(title);

        gui.addWindow(window);
    }

    protected void popupMessageDialog(MultiWindowTextGUI gui, String message) {
        new MessageDialogBuilder()
                .setTitle("Warning")
                .setText(message)
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(gui);
    }

    protected void popupMessageDialog(MultiWindowTextGUI gui, String message, String title) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText(message)
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(gui);
    }

    protected String askForAString(MultiWindowTextGUI gui, String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .build()
                .showDialog(gui);
    }

    protected String askForANumber(MultiWindowTextGUI gui, String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .setValidationPattern(Pattern.compile("[0-9]+"), "Not a number")
                .build()
                .showDialog(gui);
    }
}
