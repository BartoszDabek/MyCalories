package pl.mycalories.error;

import org.springframework.http.HttpStatus;

public enum MealErrors implements AbstractError{
    CONFLICT(HttpStatus.CONFLICT, "You are trying to modify NO-MODIFIER-FIELDS"),
    OK(HttpStatus.OK, "It's OK. I guess...");

    private final HttpStatus httpStatus;
    private final String errMsg;

    private MealErrors(HttpStatus httpStatus, String errMsg) {
        this.httpStatus = httpStatus;
        this.errMsg = errMsg;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMsg() {
        return errMsg;
    }
}
