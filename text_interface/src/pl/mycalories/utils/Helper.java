package pl.mycalories.utils;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class Helper {

    public static void exceptionMessageDialog(MultiWindowTextGUI gui, String message) {
        new MessageDialogBuilder()
                .setTitle("Warning")
                .setText(message)
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(gui);
    }

    public static String askForAString(MultiWindowTextGUI gui, String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .build()
                .showDialog(gui);
    }

    public static String askForANumber(MultiWindowTextGUI gui, String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .setValidationPattern(Pattern.compile("[0-9]+"), "Not a number")
                .build()
                .showDialog(gui);
    }
}
