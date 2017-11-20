package pl.mycalories.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import pl.mycalories.Main;
import pl.mycalories.model.Opinion;
import pl.mycalories.model.Product;
import pl.mycalories.service.OpinionService;
import pl.mycalories.utils.Helper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ProductOpinions extends AbstractWindow {

    private OpinionService opinionService = Main.ctx.getBean(OpinionService.class);
    private Product product;

    public ProductOpinions(MultiWindowTextGUI gui, Product product) {
        super(gui);
        this.product = product;
        init();
    }

    private void init() {
        new ActionListDialogBuilder()
                .setTitle("Opinions")
                .addAction("View opinions", () -> displayOpinions())
                .addAction("Add opinion", () -> {
                    if (Login.isLoggedIn()) {
                        addOpinion();
                    } else {
                        popupMessageDialog(gui, "You have to login first!");
                    }
                })
                .build()
                .showDialog(gui);
    }

    private void displayOpinions() {
        BasicWindow window = new BasicWindow("Opinions");

        Panel mainPanel = new Panel(new BorderLayout());

        TextBox textBox = new TextBox("", TextBox.Style.MULTI_LINE);
        textBox.setLayoutData(BorderLayout.Location.CENTER);
        textBox.setReadOnly(true);

        for (Opinion o : product.getOpinions()) {
            textBox.addLine("Date: " + o.getAddDate() + "\n" +
                    "Rating: " + (o.getRating() == null ? "NOT RATED" : o.getRating()) + "\n" +
                    "Added by: " + o.getUser().getUsername() + "\n\n" +
                    "Description: " + o.getDescription() + "\n" +
                    "---------------------------------------------------------------------------\n");
        }

        if (textBox.getText().equals("")) {
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

    private void addOpinion() {
        BasicWindow window = new BasicWindow("Add opinion");
        String max60Characters = "^.{0,60}$";

        Panel mainPanel = new Panel();

        TextBox description = new TextBox(new TerminalSize(65, 1));
        description.setValidationPattern(Pattern.compile(max60Characters));

        Map<Integer, String> ratingMap = new HashMap<>();
        ratingMap.put(null, "NO RATED");
        ratingMap.put(1, "1 star");
        ratingMap.put(2, "2 stars");
        ratingMap.put(3, "3 stars");
        ratingMap.put(4, "4 stars");
        ratingMap.put(5, "5 stars");

        ComboBox<String> ratingBox = new ComboBox<String>();
        ratingBox.isReadOnly();
        ratingMap.forEach((k, v) -> ratingBox.addItem(v));


        mainPanel.addComponent(new Label("Description(max 60 characters)"));
        mainPanel.addComponent(description);
        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        mainPanel.addComponent(Panels.horizontal(
                ratingBox.withBorder(Borders.singleLineBevel("Rating"))
        ));

        mainPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)));

        mainPanel.addComponent(new Button("OK", () -> {
            Opinion opinion = new Opinion();
            opinion.setAddDate(LocalDate.now());
            opinion.setDescription(description.getText());
            opinion.setProduct(product);
            opinion.setUser(Login.getCurrentUser());
            opinion.setRating(Helper.getKeyByValue(ratingMap, ratingBox.getText()));

            opinionService.save(opinion);

            window.close();
        }));

        window.setComponent(mainPanel);
        window.setCloseWindowWithEscape(true);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        gui.addWindow(window);
    }
}
