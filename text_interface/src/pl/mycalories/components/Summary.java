package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import pl.mycalories.Main;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Day;
import pl.mycalories.model.Meal;
import pl.mycalories.service.DailyCaloriesService;

import java.time.LocalDate;
import java.util.Arrays;

public class Summary extends AbstractWindow {

    private DailyCaloriesService dailyCaloriesService = Main.ctx.getBean(DailyCaloriesService.class);
    private DailyCalories dailyCalories;

    private Panel mainPanel;
    private Panel headerPanel;
    private Panel nutritionalValuesPanel;
    private Panel buttonPanel;

    private Label caloriesLabel;

    private Label proteinsLabel;
    private Label fatsLabel;
    private Label carbsLabel;

    private TextBox dateTextBox;
    private ActionListBox mealsListBox;

    private LocalDate choosenDate;

    public Summary(MultiWindowTextGUI gui, String title) {
        super(gui, title);
        this.choosenDate = LocalDate.now();
        init();
    }

    public Summary(MultiWindowTextGUI gui, String title, LocalDate choosenDate) {
        super(gui, title);
        this.choosenDate = choosenDate;
        init();
    }

    private void init() {
        initPanels();
        initComponents();

        headerPanel.addComponent(new Label("Enter the day: "));
        headerPanel.addComponent(dateTextBox);
        headerPanel.addComponent(new Button("CALCULATE", () -> calculateChoosenDay()));

        addComponentsToNutritionalPanel();

        buttonPanel.addComponent(new Button("Add meal", () -> addNewMeal()));
        buttonPanel.addComponent(new Button("Remove meal", () -> deleteMeal()));
        buttonPanel.addComponent(new Button("Close", () -> window.close()));

        addComponentsToMainPanel();

        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
    }

    private void initPanels() {
        mainPanel = new Panel();
        headerPanel = new Panel(new GridLayout(3));
        nutritionalValuesPanel = new Panel(new GridLayout(4));
        buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
    }

    private void initComponents() {
        dailyCalories = dailyCaloriesService.findByDate(Login.getCurrentUser(), choosenDate);

        dateTextBox = new TextBox(choosenDate.toString());
        mealsListBox = new ActionListBox();

        if (dailyCalories != null) {
            caloriesLabel = new Label(dailyCalories.getNutritionalValues().getCalories().toString());
            proteinsLabel = new Label(dailyCalories.getNutritionalValues().getProteins().toString());
            fatsLabel = new Label(dailyCalories.getNutritionalValues().getFats().toString());
            carbsLabel = new Label(dailyCalories.getNutritionalValues().getCarbs().toString());
        } else {
            caloriesLabel = new Label("0");
            proteinsLabel = new Label("0");
            fatsLabel = new Label("0");
            carbsLabel = new Label("0");
        }
        addMealsToListBox();
    }

    private void addMealsToListBox() {
        mealsListBox.addItem(" >> MEALS <<", () -> {
            try {
                ActionListDialogBuilder meals = new ActionListDialogBuilder()
                        .setTitle("Meals")
                        .setDescription("Choose a meal to manage it");

                for (Meal m : dailyCalories.getMeals()) {
                    meals.addAction(m.getName() + " - " + m.getNutritionalValues().getCalories() + "KCAL",
                            () -> {
                                window.close();
                                new Meals(gui, m.getName(), m);
                            });
                }

                meals.build().showDialog(gui);
            } catch (Exception e) {
                popupMessageDialog(gui, "No meals on this day!");
            }
        }).setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
    }

    private void calculateChoosenDay() {
        try {
            dailyCalories = dailyCaloriesService.findByDate(
                    Login.getCurrentUser(), LocalDate.parse(dateTextBox.getText()));

            mealsListBox.clearItems();

            if (dailyCalories == null) {
                caloriesLabel.setText("0");
                proteinsLabel.setText("0");
                fatsLabel.setText("0");
                carbsLabel.setText("0");
            } else {
                caloriesLabel.setText(dailyCalories.getNutritionalValues().getCalories().toString());
                proteinsLabel.setText(dailyCalories.getNutritionalValues().getProteins().toString());
                fatsLabel.setText(dailyCalories.getNutritionalValues().getFats().toString());
                carbsLabel.setText(dailyCalories.getNutritionalValues().getCarbs().toString());
            }

            addMealsToListBox();
        } catch (Exception e) {
            popupMessageDialog(gui, "Wrong date format. It should be YYYY-MM-DD");
        }
    }

    private void addComponentsToNutritionalPanel() {
        nutritionalValuesPanel.addComponent(new Label("Calories: "));
        nutritionalValuesPanel.addComponent(caloriesLabel);

        nutritionalValuesPanel.addComponent(new Label("Proteins: "));
        nutritionalValuesPanel.addComponent(proteinsLabel);

        nutritionalValuesPanel.addComponent(new Label("Fats: "));
        nutritionalValuesPanel.addComponent(fatsLabel);

        nutritionalValuesPanel.addComponent(new Label("Carbs: "));
        nutritionalValuesPanel.addComponent(carbsLabel);
    }

    private void addNewMeal() {
        String mealName = askForAString(gui, "Enter the name of the meal");

        if (mealName != null) {
            Meal meal = new Meal();
            meal.setName(mealName);

            if (dailyCalories == null) {
                createNewDailyCalories();
            }

            meal.setDailyCalories(dailyCalories);

            dailyCalories.getMeals().add(meal);
            dailyCalories = dailyCaloriesService.save(dailyCalories);

            popupMessageDialog(gui, "Meal added successfully!", "Info");
            window.close();
            new Summary(gui, "Summary", dailyCalories.getDay().getDate());
        }
    }

    private void createNewDailyCalories() {
        dailyCalories = new DailyCalories();

        Day day = new Day();
        day.setUser(Login.getCurrentUser());
        day.setDate(LocalDate.parse(dateTextBox.getText()));

        dailyCalories.setDay(day);
    }

    private void deleteMeal() {
        try {


            ActionListDialogBuilder meals = new ActionListDialogBuilder()
                    .setTitle("Deleting")
                    .setDescription("Choose a meal you want to delete");

            for (Meal m : dailyCalories.getMeals()) {
                meals.addAction(m.getName() + " - " + m.getNutritionalValues().getCalories() + "KCAL",
                        () -> {
                            dailyCalories.getMeals().remove(m);
                            dailyCalories = dailyCaloriesService.save(dailyCalories);
                            popupMessageDialog(gui, m.getName() + " deleted successfully", "Info");
                            window.close();
                            new Summary(gui, "Summary", dailyCalories.getDay().getDate());
                        });
            }

            meals.build().showDialog(gui);
        } catch (Exception e) {
            popupMessageDialog(gui, "No meals to remove!");
        }
    }

    private void addComponentsToMainPanel() {
        mainPanel.addComponent(headerPanel);

        mainPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)));

        mainPanel.addComponent(nutritionalValuesPanel);
        mainPanel.addComponent(mealsListBox);

        mainPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill)));

        mainPanel.addComponent(buttonPanel);
    }
}
