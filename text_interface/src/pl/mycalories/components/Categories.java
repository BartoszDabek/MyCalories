package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import pl.mycalories.Main;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

import java.util.Arrays;
import java.util.List;

public class Categories extends AbstractWindow {

    private CategoryService categoryService = Main.ctx.getBean(CategoryService.class);
    private Table<String> table;
    private TableModel<String> model;
    private List<Category> categories;

    public Categories(MultiWindowTextGUI gui, String title) {
        super(gui, title);
        init();
    }

    private void init() {
        categories = categoryService.getAll();
        table = new Table<String>("#", "Name");
        model = table.getTableModel();

        int counter = 1;
        for (Category c : categories) {
            model.addRow(Integer.toString(counter), c.getName());
            counter++;
        }

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

        if (Login.isLoggedIn() && Login.hasAdminRights()) {
            buttonPanel.addComponent(new Button("Add..", () -> addNewCategory()));
            buttonPanel.addComponent(new Button("Remove..", () -> removeCategory()));
        }
        buttonPanel.addComponent(new Button("Close", () -> window.close()));

        window.setComponent(Panels.vertical(
                table.withBorder(Borders.singleLineBevel()),
                buttonPanel
        ));
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
    }

    private void addNewCategory() {
        String name = askForAString(gui, "Enter category name");

        if (name != null) {
            try {
                if (name.trim().equals("")) {
                    popupMessageDialog(gui, "Adding unsuccessful - missing name!");
                    return;
                }

                Category category = new Category();
                category.setName(name);
                Category savedCategory = categoryService.save(category);

                if (savedCategory != null) {
                    model.addRow(Integer.toString(model.getRowCount() + 1), savedCategory.getName());
                }
            } catch (Exception e) {
                popupMessageDialog(gui, "Adding unsuccessful - category with that name already exists");
            }
        }
    }

    private void removeCategory() {
        String numberAsText = askForANumber(gui, "Enter row # to remove(1 -" + model.getRowCount() + ")");

        if (numberAsText != null) {
            try {
                int rowToDelete = Integer.parseInt(numberAsText) - 1;
                String nameToDelete = model.getCell(1, rowToDelete);
                Long deletedCategory = categoryService.deleteByName(nameToDelete);

                if (deletedCategory == 1) {
                    model.removeRow(rowToDelete);
                    for (int i = rowToDelete; i < model.getRowCount(); i++) {
                        model.setCell(0, i, Integer.toString(i + 1));
                    }
                }
            } catch (Exception e) {
                popupMessageDialog(gui, "Deleting unsuccessful");
            }
        }
    }

}
