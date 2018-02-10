package pl.mycalories.service.impl;

import org.springframework.stereotype.Service;
import pl.mycalories.csv.CSVUtils;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.model.Meal;
import pl.mycalories.model.ProductMeal;
import pl.mycalories.service.CsvService;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public String generateCsv(DailyCalories dailyCalories) throws IOException {
        Writer writer = new StringWriter();

        CSVUtils.writeLine(writer, Arrays.asList(dailyCalories.getDay().getDate().toString(), "Products", "Proteins", "Fats", "Carbs", "Calories"));

        List<String> allDay = new ArrayList<>();
        allDay.add("");
        allDay.add("TOTAL");
        allDay.add(dailyCalories.getNutritionalValues().getProteins().toString());
        allDay.add(dailyCalories.getNutritionalValues().getFats().toString());
        allDay.add(dailyCalories.getNutritionalValues().getCarbs().toString());
        allDay.add(dailyCalories.getNutritionalValues().getCalories().toString());

        CSVUtils.writeLine(writer, allDay);

        for (Meal meal : dailyCalories.getMeals()) {
            CSVUtils.writeLine(writer, Collections.singletonList(meal.getName()));
            List<String> meals = new ArrayList<>();
            for(ProductMeal pm: meal.getProductMeals()) {
                List<String> pms = new ArrayList<>();
                pms.add("");
                pms.add(pm.getProduct().getName() + " x" + pm.getAmount().toString());
                pms.add(String.valueOf(pm.getProduct().getNutritionalValues().getProteins() * pm.getAmount()));
                pms.add(String.valueOf(pm.getProduct().getNutritionalValues().getFats() * pm.getAmount()));
                pms.add(String.valueOf(pm.getProduct().getNutritionalValues().getCarbs() * pm.getAmount()));
                pms.add(String.valueOf(pm.getProduct().getNutritionalValues().getCalories() * pm.getAmount()));

                CSVUtils.writeLine(writer, pms);
            }
            meals.add("");
            meals.add("sum");
            meals.add(meal.getNutritionalValues().getProteins().toString());
            meals.add(meal.getNutritionalValues().getFats().toString());
            meals.add(meal.getNutritionalValues().getCarbs().toString());
            meals.add(meal.getNutritionalValues().getCalories().toString());

            CSVUtils.writeLine(writer, meals);
        }

        return writer.toString();
    }
}
