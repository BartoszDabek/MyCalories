package pl.mycalories.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import pl.mycalories.Main;
import pl.mycalories.model.User;
import pl.mycalories.service.UserService;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Register extends AbstractWindow {

    private UserService userService = Main.ctx.getBean(UserService.class);
    private TextBox usernameTextBox, passwordTextBox, confirmpassTextBox, emailTextBox;

    public Register(MultiWindowTextGUI gui, String title) {
        super(gui, title);
        init();
    }

    private void init() {
        Panel mainPanel = new Panel(new GridLayout(2));
        initTextBoxes();

        mainPanel.addComponent(new Label("Username"));
        mainPanel.addComponent(usernameTextBox);

        mainPanel.addComponent(new Label("Password"));
        mainPanel.addComponent(passwordTextBox);

        mainPanel.addComponent(new Label("Confirm password"));
        mainPanel.addComponent(confirmpassTextBox);

        mainPanel.addComponent(new Label("Email"));
        mainPanel.addComponent(emailTextBox);

        mainPanel.addComponent(new Button("Register", () -> {
            if (inputsAreCorrect()) {
                registerUser();
            }
        }));
        mainPanel.addComponent(new Button("Cancel", () -> window.close()));

        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
    }

    private void initTextBoxes() {
        TerminalSize terminalSize = new TerminalSize(20, 1);

        usernameTextBox = new TextBox()
                .setPreferredSize(terminalSize);

        passwordTextBox = new TextBox()
                .setPreferredSize(terminalSize)
                .setMask('*');

        confirmpassTextBox = new TextBox()
                .setPreferredSize(terminalSize)
                .setMask('*');

        emailTextBox = new TextBox()
                .setPreferredSize(terminalSize);
    }

    private boolean inputsAreCorrect() {
        if (fieldsAreFilled()) {
            if (validatePassword()) {
                if (validateEmail()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fieldsAreFilled() {
        if (Stream.of(usernameTextBox, passwordTextBox, emailTextBox).anyMatch(x -> x.getText().trim().equals(""))) {
            popupMessageDialog(gui, "You have to fill all fields!");
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        if (passwordTextBox.getText().equals(confirmpassTextBox.getText())) {
            return true;
        } else {
            popupMessageDialog(gui, "Passwords do not match!");
            return false;
        }
    }

    private boolean validateEmail() {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailTextBox.getText());
        if (matcher.find()) {
            return true;
        } else {
            popupMessageDialog(gui, "Wrong email address!");
            return false;
        }
    }

    private void registerUser() {
        try {
            User user = new User();
            user.setUsername(usernameTextBox.getText());
            user.setPassword(passwordTextBox.getText());
            user.setEmail(emailTextBox.getText());

            userService.save(user);

            popupMessageDialog(gui, "Account created successfully", "Info");
            window.close();
        } catch (Exception e) {
            popupMessageDialog(gui, "Username or email address is already taken!");
        }
    }

}
