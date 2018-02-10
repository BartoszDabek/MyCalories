package pl.mycalories.service;

import pl.mycalories.model.DailyCalories;

import java.io.IOException;

public interface CsvService {
    String generateCsv(DailyCalories dailyCalories) throws IOException;
}
