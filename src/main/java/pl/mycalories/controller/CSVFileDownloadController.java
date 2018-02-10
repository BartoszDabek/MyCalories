package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mycalories.error.ErrorInformation;
import pl.mycalories.model.DailyCalories;
import pl.mycalories.security.SecurityUtils;
import pl.mycalories.service.CsvService;
import pl.mycalories.service.DailyCaloriesService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/downloadCSV")
public class CSVFileDownloadController {

    private DailyCaloriesService dailyCaloriesService;
    private CsvService csvService;

    @Autowired
    public void setDailyCaloriesService(DailyCaloriesService dailyCaloriesService) {
        this.dailyCaloriesService = dailyCaloriesService;
    }

    @Autowired
    public void setCsvService(CsvService csvService) {
        this.csvService = csvService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam("date") String date, HttpServletResponse response) throws IOException {
        DailyCalories obj = dailyCaloriesService.findByDate(SecurityUtils.getCurrentUser(), LocalDate.parse(date));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=Daily-report.csv");

        if (obj == null) {
            ErrorInformation errorInformation = new ErrorInformation(HttpStatus.NOT_FOUND, "No content to export");
            return new ResponseEntity<>(errorInformation, HttpStatus.NOT_FOUND);
        }

        String csvFormat = csvService.generateCsv(obj);

        return new ResponseEntity<>(csvFormat, HttpStatus.OK);
    }
}