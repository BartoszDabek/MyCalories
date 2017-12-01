package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import pl.mycalories.Main;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Meal;
import pl.mycalories.service.DailyCaloriesService;

import java.time.LocalDate;
import java.util.Arrays;

public class Summary extends AbstractWindow {

    private DailyCaloriesService dailyCaloriesService = Main.ctx.getBean(DailyCaloriesService.class);

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

    public Summary(MultiWindowTextGUI gui, String title) {
        super(gui, title);
        init();
    }

    private void init() {
        initPanels();
        initComponents();

        headerPanel.addComponent(new Label("Enter the day: "));
        headerPanel.addComponent(dateTextBox);
        headerPanel.addComponent(new Button("CALCULATE", () -> calculateChoosenDay()));

        addComponentsToNutritionalPanel();

        buttonPanel.addComponent(new Button("Add meal", () -> System.out.println("dodaje")));
        buttonPanel.addComponent(new Button("Remove meal", () -> System.out.println("usuwam")));
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
        DailyCalories dailyCalories = dailyCaloriesService.findByDate(Login.getCurrentUser(), LocalDate.now());

        dateTextBox = new TextBox(LocalDate.now().toString());
        mealsListBox = new ActionListBox();

        if(dailyCalories != null) {
            addMealsToListBox(dailyCalories);
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

    }

    private void addMealsToListBox(DailyCalories dailyCalories) {
        mealsListBox.addItem(" >> MEALS <<", () -> {
            ActionListDialogBuilder meals = new ActionListDialogBuilder()
                    .setTitle("Meals")
                    .setDescription("Choose meal to display details");

            for(Meal m: dailyCalories.getMeals()) {
                meals.addAction(m.getName(), () -> System.out.println(m.getName()));
            }

            meals.build().showDialog(gui);
        }).setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
    }

    private void calculateChoosenDay() {
        try {
            DailyCalories dailyCalories = dailyCaloriesService.findByDate(
                    Login.getCurrentUser(), LocalDate.parse(dateTextBox.getText()));

            mealsListBox.clearItems();

            if(dailyCalories == null) {
                caloriesLabel.setText("0");
                proteinsLabel.setText("0");
                fatsLabel.setText("0");
                carbsLabel.setText("0");
            } else {
                caloriesLabel.setText(dailyCalories.getNutritionalValues().getCalories().toString());
                proteinsLabel.setText(dailyCalories.getNutritionalValues().getProteins().toString());
                fatsLabel.setText(dailyCalories.getNutritionalValues().getFats().toString());
                carbsLabel.setText(dailyCalories.getNutritionalValues().getCarbs().toString());

                addMealsToListBox(dailyCalories);
            }
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
