package pl.mycalories.error;

import org.springframework.http.HttpStatus;

public class ErrorInformation {
    private AbstractError errorMsg;

    public ErrorInformation(AbstractError errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HttpStatus getHttpStatus() {
        return errorMsg.getHttpStatus();
    }

    public String getErrorMsg() {
        return errorMsg.getErrorMsg();
    }
}