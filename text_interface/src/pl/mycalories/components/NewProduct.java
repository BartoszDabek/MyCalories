package pl.mycalories.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.TableModel;
import pl.mycalories.Main;
import pl.mycalories.utils.Helper;
import pl.mycalories.model.Category;
import pl.mycalories.model.NutritionalValues;
import pl.mycalories.model.Product;
import pl.mycalories.service.CategoryService;
import pl.mycalories.service.ProductService;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class NewProduct {

    private final String regexPattern = "^\\d{0,4}$";

    private MultiWindowTextGUI gui;
    private BasicWindow window;

    private ProductService productService = Main.ctx.getBean(ProductService.class);
    private CategoryService categoryService = Main.ctx.getBean(CategoryService.class);

    private Map<Long, String> categoriesMap;
    private ComboBox<String> categoriesBox;

    private Panel mainPanel;
    private Panel boxPanel;
    private Panel buttonPanel;

    private TextBox name;
    private TextBox calories;
    private TextBox proteins;
    private TextBox fats;
    private TextBox carbs;

    private TableModel<String> model;

    public NewProduct(MultiWindowTextGUI gui, TableModel<String> model) {
        this.gui = gui;
        this.model = model;
        init();
    }

    private void init() {
        initComponents();
        addBoxPanelComponents();
        mainPanel.addComponent(boxPanel);

        getCategoriesAndAddToCategoriesBox();
        addCategoriesBoxToMainPanel();
        mainPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)));

        buttonPanel.addComponent(new Button("Create", () -> {
            addNewProduct();
        }));
        buttonPanel.addComponent(new Button("Cancel", () -> window.close()));

        mainPanel.addComponent(buttonPanel);

        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
        gui.addWindow(window);
    }

    private void initComponents() {
        window = new BasicWindow("New product creator");

        categoriesMap = new HashMap<Long, String>();
        categoriesBox = new ComboBox<String>();

        mainPanel = new Panel();
        boxPanel = new Panel(new GridLayout(2));
        buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

        name = new TextBox();
        calories = new TextBox().setValidationPattern(Pattern.compile(regexPattern));
        proteins = new TextBox().setValidationPattern(Pattern.compile(regexPattern));
        fats = new TextBox().setValidationPattern(Pattern.compile(regexPattern));
        carbs = new TextBox().setValidationPattern(Pattern.compile(regexPattern));
    }

    private void addBoxPanelComponents() {
        boxPanel.addComponent(new Label("Name"));
        boxPanel.addComponent(name);
        boxPanel.addComponent(new Label("Calories"));
        boxPanel.addComponent(calories);
        boxPanel.addComponent(new Label("Proteins"));
        boxPanel.addComponent(proteins);
        boxPanel.addComponent(new Label("Fats"));
        boxPanel.addComponent(fats);
        boxPanel.addComponent(new Label("Carbs"));
        boxPanel.addComponent(carbs);
    }

    private void getCategoriesAndAddToCategoriesBox() {
        List<Category> categories = categoryService.getAll();

        for (Category c : categories) {
            categoriesMap.put(c.getId(), c.getName());
        }

        List<String> list = new ArrayList<String>(categoriesMap.values());
        for (String s : list) {
            categoriesBox.addItem(s);
        }
    }

    private void addCategoriesBoxToMainPanel() {
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
                Helper.exceptionMessageDialog(gui, "            Some error occured!\nMaybe product with such a name already exists");
            }
        } else {
            Helper.exceptionMessageDialog(gui, "You have to fill all fields!");
        }
    }

    private boolean fieldsAreFilled() {
        if (Stream.of(name, calories, proteins, fats, carbs).anyMatch(x -> x.getText().trim().equals(""))) {
            return false;
        }
        return true;
    }

}
