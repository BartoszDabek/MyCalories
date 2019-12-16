package pl.mycalories.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.mycalories.CalorieeCApplication;
import pl.mycalories.model.Category;

import java.util.UnknownFormatConversionException;

@SpringBootTest(classes = CalorieeCApplication.class)
public class NameConverterTest {

    private NameConverter nameConverter;
    private Category category;
    private String expectedString = "Name test";

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init() {
        category = new Category();
        nameConverter = new NameConverter(category);
    }

    @Test
    public void should_capitalize_first_letter() throws UnknownFormatConversionException {
        category.setName("name test");
        nameConverter.convertName();
        Assert.assertEquals(expectedString, category.getName());
    }

    @Test
    public void should_remove_leading_whitespaces() throws UnknownFormatConversionException {
        category.setName("        Name test");
        nameConverter.convertName();
        Assert.assertEquals(expectedString, category.getName());
    }

    @Test
    public void should_convert_string_to_lower_case_and_capitalize_first_letter() throws UnknownFormatConversionException {
        category.setName("NAME TEST");
        nameConverter.convertName();
        Assert.assertEquals(expectedString, category.getName());
    }

    @Test
    public void should_throw_exception() throws UnknownFormatConversionException {
        expectedEx.expect(UnknownFormatConversionException.class);

        category.setName("        ");
        nameConverter.convertName();
    }

    @Test
    public void should_return_exception_message() throws UnknownFormatConversionException {
        expectedEx.expectMessage("Name field should contain body!");

        category.setName("  ");
        nameConverter.convertName();
    }

}
