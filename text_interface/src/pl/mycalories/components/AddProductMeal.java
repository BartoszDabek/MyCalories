package pl.mycalories.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import pl.mycalories.Main;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Meal;
import pl.mycalories.model.Product;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.DailyCaloriesService;
import pl.mycalories.service.ProductService;
import pl.mycalories.utils.Helper;

import java.util.*;
import java.util.regex.Pattern;

import static pl.mycalories.utils.Helper.INPUT_NUMBERS_REGEX;

public class AddProductMeal extends AbstractWindow {

    private Meal meal;

    private DailyCaloriesService dailyCaloriesService = Main.ctx.getBean(DailyCaloriesService.class);
    private ProductService productService = Main.ctx.getBean(ProductService.class);

    private Map<Product, String> productsMap;
    private ComboBox<String> productsDropDown;
    private TextBox quantityTextBox;

    private Panel mainPanel;
    private Panel buttonPanel;

    public AddProductMeal(MultiWindowTextGUI gui, String title, Meal meal) {
        super(gui, title);
        this.meal = meal;
        init();
    }

    private void init() {
        initPanels();

        buttonPanel.addComponent(new Button("Add", () -> add()));
        buttonPanel.addComponent(new Button("Cancel", () -> closeWindowAndGoToMealWindow()));

        initComponents();
        getProductsAndMapThem();
        addProductsToDropDown();
        addComponentsToMainPanel();

        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
    }

    private void initPanels() {
        mainPanel = new Panel();
        buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
    }

    private void add() {
        if(quantityTextBox.getText().equals("")) {
            popupMessageDialog(gui, "You have to fill quantity value!");
        } else {
            addProductMeal();
            closeWindowAndGoToMealWindow();
        }
    }

    private void addProductMeal() {
        String selectedProductName = productsDropDown.getSelectedItem();
        Product selectedProduct = Helper.getKeyByValue(productsMap, selectedProductName);

        int productQuantity = Integer.parseInt(quantityTextBox.getText());

        DailyCalories dailyCalories = meal.getDailyCalories();

        ProductMeal productMeal = new ProductMeal();
        productMeal.setProduct(selectedProduct);
        productMeal.setAmount(productQuantity);
        productMeal.setMeal(meal);
        meal.getProductMeals().add(productMeal);

        dailyCalories.getMeals().add(meal);

        dailyCalories = dailyCaloriesService.save(dailyCalories);
        meal = dailyCalories.getMeals()
                .stream()
                .filter(dc -> dc.getId().equals(meal.getId()))
                .findFirst()
                .get();

    }

    private void closeWindowAndGoToMealWindow() {
        window.close();
        new Meals(gui, meal.getName(), meal);
    }

    private void initComponents() {
        productsMap = new HashMap<>();
        productsDropDown = new ComboBox<>();
        quantityTextBox = new TextBox()
                .setValidationPattern(Pattern.compile(INPUT_NUMBERS_REGEX));
    }

    private void getProductsAndMapThem() {
        List<Product> products= productService.getAll();
        products.forEach(product -> productsMap.put(product, product.getName()));
    }

    private void addProductsToDropDown() {
        List<String> productsList = new ArrayList<String>(productsMap.values());
        productsList.forEach(name -> productsDropDown.addItem(name));
    }

    private void addComponentsToMainPanel() {
        mainPanel.addComponent(Panels.horizontal(
                new Label("Quantity"),
                quantityTextBox
        ));

        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        mainPanel.addComponent(Panels.horizontal(
                productsDropDown.withBorder(Borders.singleLineBevel("Product"))
        ));

        mainPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)));

        mainPanel.addComponent(buttonPanel);
    }
}
