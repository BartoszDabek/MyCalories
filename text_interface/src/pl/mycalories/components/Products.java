package pl.mycalories.components;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import pl.mycalories.Main;
import pl.mycalories.utils.Helper;
import pl.mycalories.model.Product;
import pl.mycalories.service.ProductService;

import java.util.Arrays;
import java.util.List;

public class Products {

    private MultiWindowTextGUI gui;
    private ProductService productService = Main.ctx.getBean(ProductService.class);
    private Table<String> table;
    private TableModel<String> model;
    private List<Product> products;

    public Products(MultiWindowTextGUI gui) {
        this.gui = gui;
        init();
    }

    private void init() {
        final BasicWindow window = new BasicWindow("Products");

        products = productService.getAll();
        table = new Table<String>("#",
                "Name",
                "Category",
                "Calories",
                "Proteins",
                "Fats",
                "Carbs");
        model = table.getTableModel();

        int counter = 1;
        for (Product p : products) {
            model.addRow(Integer.toString(counter),
                    p.getName(),
                    p.getCategory().getName(),
                    Integer.toString(p.getNutritionalValues().getCalories()),
                    Integer.toString(p.getNutritionalValues().getProteins()),
                    Integer.toString(p.getNutritionalValues().getFats()),
                    Integer.toString(p.getNutritionalValues().getCarbs()));

            counter++;
        }

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

        buttonPanel.addComponent(new Button("Add..", () -> new NewProduct(gui, model)));
        buttonPanel.addComponent(new Button("Remove..", () -> removeProduct()));
        buttonPanel.addComponent(new Button("Close", () -> window.close()));

        window.setComponent(Panels.vertical(
                table.withBorder(Borders.singleLineBevel()),
                buttonPanel
        ));
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setCloseWindowWithEscape(true);
        gui.addWindow(window);
    }

    private void removeProduct() {
        String numberAsText = Helper.askForANumber(gui, "Enter row # to remove(1 -" + model.getRowCount() + ")");

        if (numberAsText != null) {
            try {
                int rowToDelete = Integer.parseInt(numberAsText) - 1;
                String nameToDelete = model.getCell(1, rowToDelete);
                Long deletedCategory = productService.deleteByName(nameToDelete);

                if (deletedCategory == 1) {
                    model.removeRow(rowToDelete);
                    for (int i = rowToDelete; i < model.getRowCount(); i++) {
                        model.setCell(0, i, Integer.toString(i + 1));
                    }
                }
            } catch (Exception e) {
                Helper.exceptionMessageDialog(gui, "Deleting unsuccessful");
            }
        }
    }
}
