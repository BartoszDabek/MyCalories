package pl.mycalories.components;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import pl.mycalories.model.Opinion;
import pl.mycalories.model.Product;

import java.util.Arrays;

public class ProductOpinions {

    private BasicWindow window;
    private MultiWindowTextGUI gui;
    private Product product;

    public ProductOpinions(MultiWindowTextGUI gui, Product product) {
        this.gui = gui;
        this.product = product;
        init();
    }

    private void init() {
        new ActionListDialogBuilder()
                .setTitle("Opinions")
                .addAction("View opinions", () -> displayOpinions())
                .addAction("Add opinion", () -> {
                    System.out.println("Dodaje opinie");
                })
                .build()
                .showDialog(gui);
    }

    private void displayOpinions() {
        window = new BasicWindow("Opinions");

        Panel mainPanel = new Panel(new BorderLayout());
        TextBox textBox = new TextBox("", TextBox.Style.MULTI_LINE);
        textBox.setLayoutData(BorderLayout.Location.CENTER);
        textBox.setReadOnly(true);

        for(Opinion o: product.getOpinions()) {
            textBox.addLine("Date: " + o.getAddDate() + "\n" +
                "Rating: " + o.getRating() + "\n" +
                "Added by: " + o.getUser().getUsername() + "\n\n" +
                "Description: " + o.getDescription() + "\n" +
                "---------------------------------------------------------------------------\n");
        }

        if(textBox.getText().equals("")) {
            textBox.setText("This product doesn't have any opinion yet! Be first and leave your opinion!");
        }

        mainPanel.addComponent(textBox);

        Label label = new Label("                            Press ESC to go back");
        label.setForegroundColor(TextColor.ANSI.RED);
        mainPanel.addComponent(label);

        window.setComponent(mainPanel);
        window.setCloseWindowWithEscape(true);
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        gui.addWindow(window);
    }
}
