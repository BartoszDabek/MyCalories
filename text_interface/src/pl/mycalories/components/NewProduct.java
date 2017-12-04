package pl.mycalories.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.TableModel;
import pl.mycalories.Main;
import pl.mycalories.model.Category;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.Product;
import pl.mycalories.service.CategoryService;
import pl.mycalories.service.ProductService;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static pl.mycalories.utils.Helper.INPUT_NUMBERS_REGEX;

public class NewProduct extends AbstractWindow {

    private ProductService productService = Main.ctx.getBean(ProductService.class);
    private CategoryService categoryService = Main.ctx.getBean(CategoryService.class);

    private Map<Long, String> categoriesMap;
    private ComboBox<String> categoriesBox;

    private Panel mainPanel;
    private Panel inputsAndLabelsPanel;
    private Panel buttonPanel;

    private TextBox name;
    private TextBox calories;
    private TextBox proteins;
    private TextBox fats;
    private TextBox carbs;

    private TableModel<String> model;

    public NewProduct(MultiWindowTextGUI gui, String title, TableModel<String> model) {
        super(gui, title);
        this.model = model;
        init();
    }

    private void init() {
        initComponents();
        addInputAndLabelsToScreen();
        getCategoriesAndAddToScreen();

        mainPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)));

        buttonPanel.addComponent(new Button("Create", () -> addNewProduct()));
        buttonPanel.addComponent(new Button("Cancel", () -> window.close()));

        mainPanel.addComponent(buttonPanel);

        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
    }

    private void initComponents() {
        mainPanel = new Panel();
        inputsAndLabelsPanel = new Panel(new GridLayout(2));
        buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
    }

    private void addInputAndLabelsToScreen() {
        name = new TextBox();
        calories = new TextBox().setValidationPattern(Pattern.compile(INPUT_NUMBERS_REGEX));
        proteins = new TextBox().setValidationPattern(Pattern.compile(INPUT_NUMBERS_REGEX));
        fats = new TextBox().setValidationPattern(Pattern.compile(INPUT_NUMBERS_REGEX));
        carbs = new TextBox().setValidationPattern(Pattern.compile(INPUT_NUMBERS_REGEX));

        inputsAndLabelsPanel.addComponent(new Label("Name"));
        inputsAndLabelsPanel.addComponent(name);
        inputsAndLabelsPanel.addComponent(new Label("Calories"));
        inputsAndLabelsPanel.addComponent(calories);
        inputsAndLabelsPanel.addComponent(new Label("Proteins"));
        inputsAndLabelsPanel.addComponent(proteins);
        inputsAndLabelsPanel.addComponent(new Label("Fats"));
        inputsAndLabelsPanel.addComponent(fats);
        inputsAndLabelsPanel.addComponent(new Label("Carbs"));
        inputsAndLabelsPanel.addComponent(carbs);

        mainPanel.addComponent(inputsAndLabelsPanel);
    }

    private void getCategoriesAndAddToScreen() {
        categoriesMap = new HashMap<Long, String>();
        categoriesBox = new ComboBox<String>();

        List<Category> categories = categoryService.getAll();
        categories.forEach(category -> categoriesMap.put(category.getId(), category.getName()));

        List<String> categoriesList = new ArrayList<String>(categoriesMap.values());
        categoriesList.forEach(name -> categoriesBox.addItem(name));

        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        mainPanel.addComponent(Panels.horizontal(
                categoriesBox.withBorder(Borders.singleLineBevel("Category"))
        ));
    }

    private void addNewProduct() {
        if (fieldsAreFilled()) {
            try {
                Product product = new Product();
                NutritionalValues nutritionalValues = new NutritionalValues(
                        Integer.parseInt(calories.getText()),
                        Integer.parseInt(proteins.getText()),
                        Integer.parseInt(fats.getText()),
                        Integer.parseInt(carbs.getText())
                );
                Category category = categoryService.findByName(categoriesBox.getText());

                product.setName(name.getText());
                product.setNutritionalValues(nutritionalValues);
                product.setCategory(category);

                Product savedProduct = productService.save(product);

                if (savedProduct != null) {
                    model.addRow(Integer.toString(model.getRowCount() + 1),
                            name.getText(),
                            categoriesBox.getText(),
                            calories.getText(),
                            proteins.getText(),
                            fats.getText(),
                            carbs.getText()
                    );
                }
                window.close();
            } catch (Exception e) {
                popupMessageDialog(gui, "            Some error occured!\nMaybe product with such a name already exists");
            }
        } else {
            popupMessageDialog(gui, "You have to fill all fields!");
        }
    }

    private boolean fieldsAreFilled() {
        return !Stream.of(name, calories, proteins, fats, carbs).anyMatch(x -> x.getText().trim().equals(""));
    }

}
