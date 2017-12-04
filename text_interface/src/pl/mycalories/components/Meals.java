package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import pl.mycalories.Main;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Meal;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.DailyCaloriesService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Meals extends AbstractWindow {

    private DailyCaloriesService dailyCaloriesService = Main.ctx.getBean(DailyCaloriesService.class);
    private Map<Integer, ProductMeal> productMealsMap = new HashMap<>();
    private Meal meal;
    private DailyCalories dailyCalories;
    private Table<String> table;
    private TableModel<String> model;

    public Meals(MultiWindowTextGUI gui, String title, Meal meal) {
        super(gui, title);
        this.meal = meal;
        this.dailyCalories = meal.getDailyCalories();
        init();
    }

    private void init() {
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Add..", () -> {
            window.close();
            new AddProductMeal(gui, "Add product to " + meal.getName(), meal);
        }));
        buttonPanel.addComponent(new Button("Remove..", () -> removeProductMeal()));
        buttonPanel.addComponent(new Button("Close", () -> {
            window.close();
            new Summary(gui, "Summary", dailyCalories.getDay().getDate());
        }));

        initTable();
        putValuesToTableAndMap();

        window.setComponent(Panels.vertical(
                table.withBorder(Borders.singleLineBevel()),
                buttonPanel
        ));
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
    }

    private void removeProductMeal() {
        String rowToRemove = askForANumber(gui, "Enter row # to remove(1 - " + (model.getRowCount() - 1) + ")");
        if (rowToRemove != null) {
            try {
                if (Integer.parseInt(rowToRemove) >= model.getRowCount()) {
                    throw new Exception();
                }

                ProductMeal pmToDelete = productMealsMap.get(Integer.parseInt(rowToRemove));
                dailyCalories.getMeals().forEach(dc -> dc.getProductMeals()
                        .removeIf(pm -> pm.getId().equals(pmToDelete.getId())));
                meal.getProductMeals().remove(pmToDelete);

                dailyCalories = dailyCaloriesService.save(dailyCalories);
                meal = dailyCalories.getMeals()
                        .stream()
                        .filter(dc -> dc.getId().equals(meal.getId()))
                        .findFirst()
                        .get();

                updateProductMealsMap(Integer.parseInt(rowToRemove) + 1);
                updateTableAfterDelete(Integer.parseInt(rowToRemove) - 1);
            } catch (Exception e) {
                popupMessageDialog(gui, "Deleting unsuccessful");
            }
        }
    }

    private void updateProductMealsMap(int startingIndex) {
        for (int i = startingIndex; i <= productMealsMap.size(); i++) {
            ProductMeal pm = productMealsMap.get(i);
            productMealsMap.put(i - 1, pm);
        }
        productMealsMap.remove(productMealsMap.size());
    }

    private void updateTableAfterDelete(int rowToRemove) {
        model.removeRow(rowToRemove);
        for (int i = rowToRemove; i < model.getRowCount() - 1; i++) {
            model.setCell(0, i, Integer.toString(i + 1));
        }
        updateSummaryRow();
    }

    private void updateSummaryRow() {
        Meal currentMeal = dailyCalories.getMeals()
                .stream()
                .filter(i -> i.getId().equals(meal.getId()))
                .findFirst()
                .get();

        if (currentMeal != null) {
            model.removeRow(model.getRowCount() - 1);
            putSummaryRowToTable(currentMeal);
        }
    }

    private void putSummaryRowToTable(Meal currentMeal) {
        model.addRow("", "", "",
                Integer.toString(currentMeal.getNutritionalValues().getCalories()),
                Integer.toString(currentMeal.getNutritionalValues().getProteins()),
                Integer.toString(currentMeal.getNutritionalValues().getFats()),
                Integer.toString(currentMeal.getNutritionalValues().getCarbs())
        );
    }

    private void initTable() {
        table = new Table<String>("#",
                "Product",
                "Amount",
                "Calories",
                "Proteins",
                "Fats",
                "Carbs");
        model = table.getTableModel();
    }

    private void putValuesToTableAndMap() {
        int counter = 1;
        for (ProductMeal pm : meal.getProductMeals()) {
            productMealsMap.put(counter, pm);
            model.addRow(Integer.toString(counter),
                    pm.getProduct().getName(),
                    Integer.toString(pm.getAmount()),
                    Integer.toString(pm.getProduct().getNutritionalValues().getCalories() * pm.getAmount()),
                    Integer.toString(pm.getProduct().getNutritionalValues().getProteins() * pm.getAmount()),
                    Integer.toString(pm.getProduct().getNutritionalValues().getFats() * pm.getAmount()),
                    Integer.toString(pm.getProduct().getNutritionalValues().getCarbs() * pm.getAmount())
            );
            counter++;
        }

        putSummaryRowToTable(meal);
    }

}
