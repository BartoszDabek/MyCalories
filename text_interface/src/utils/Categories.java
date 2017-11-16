package utils;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Categories {

    private MultiWindowTextGUI gui;
    private CategoryService service = Main.ctx.getBean(CategoryService.class);
    private Table<String> table;
    private TableModel<String> model;
    private List<Category> categories;

    public Categories(MultiWindowTextGUI gui) {
        this.gui = gui;
        init();
    }

    public List<Category> getAllCategories() {
        return service.getAll();
    }

    private void init() {
        final BasicWindow window = new BasicWindow("Categories");

        categories = getAllCategories();
        table = new Table<String>("#", "Name");
        model = table.getTableModel();

        int counter = 1;
        for (Category c : categories) {
            model.addRow(Integer.toString(counter), c.getName());
            counter++;
        }

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

        buttonPanel.addComponent(new Button("Add..", () -> addNewCategory()));
        buttonPanel.addComponent(new Button("Remove..", () -> removeCategory()));
        buttonPanel.addComponent(new Button("Close", () -> window.close()));

        window.setComponent(Panels.vertical(
                table.withBorder(Borders.singleLineBevel()),
                buttonPanel
        ));
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
        gui.addWindow(window);
    }

    private void addNewCategory() {
        String name = askForAString("Enter category name");

        if (name != null) {
            try {
                if (name.trim().equals("")) {
                    exceptionMessageDialog("Adding unsuccessful - missing name!");
                    return;
                }

                Category category = new Category();
                category.setName(name);
                Category savedCategory = service.save(category);

                if (savedCategory != null) {
                    model.addRow(Integer.toString(model.getRowCount() + 1), savedCategory.getName());
                }
            } catch (Exception e) {
                exceptionMessageDialog("Adding unsuccessful - category with that name already exists");
            }
        }
    }

    private void removeCategory() {
        String numberAsText = askForANumber("Enter row # to remove(1 -" + model.getRowCount() + ")");

        if (numberAsText != null) {
            try {
                int rowToDelete = Integer.parseInt(numberAsText) - 1;
                String nameToDelete = model.getCell(1, rowToDelete);
                Long deletedCategory = service.deleteByName(nameToDelete);

                if (deletedCategory == 1) {
                    model.removeRow(rowToDelete);
                    for (int i = rowToDelete; i < model.getRowCount(); i++) {
                        model.setCell(0, i, Integer.toString(i + 1));
                    }
                }
            } catch (Exception e) {
                exceptionMessageDialog("Deleting unsuccessful");
            }
        }
    }

    private String askForAString(String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .build()
                .showDialog(gui);
    }

    private String askForANumber(String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .setValidationPattern(Pattern.compile("[0-9]+"), "Not a number")
                .build()
                .showDialog(gui);
    }

    private void exceptionMessageDialog(String message) {
        new MessageDialogBuilder()
                .setTitle("Warning")
                .setText(message)
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(gui);
    }

}
