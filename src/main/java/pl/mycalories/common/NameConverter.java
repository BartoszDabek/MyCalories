package pl.mycalories.common;

import org.springframework.util.StringUtils;
import pl.mycalories.model.NameInterface;

import java.util.UnknownFormatConversionException;

public class NameConverter {

    private NameInterface nameInterface;

    public NameConverter(NameInterface nameInterface) {
        this.nameInterface = nameInterface;
    }

    public void convertName() throws UnknownFormatConversionException {
        removeWhiteSpaces();
        convertStringToLowerCase();
        capitalizeFirstLetter();
    }

    private void removeWhiteSpaces() throws UnknownFormatConversionException {
        nameInterface.setName(nameInterface.getName().trim());

        if(nameInterface.getName().equals("")) {
            throw new UnknownFormatConversionException("Name field should contain body!");
        }
    }

    private void convertStringToLowerCase() {
        nameInterface.setName(nameInterface.getName().toLowerCase());
    }

    private void capitalizeFirstLetter() {
        nameInterface.setName(StringUtils.capitalize(nameInterface.getName()));
    }
}
