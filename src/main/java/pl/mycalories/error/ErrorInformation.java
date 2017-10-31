package pl.mycalories.error;

import org.springframework.http.HttpStatus;

public class ErrorInformation {

    private HttpStatus errorStatus;
    private String errorMsg;

    public ErrorInformation(HttpStatus errorStatus, String errorMsg) {
        this.errorStatus = errorStatus;
        this.errorMsg = errorMsg;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}