package pl.mycalories.error;

import org.springframework.http.HttpStatus;

public interface AbstractError {
    HttpStatus getHttpStatus();
    String getErrorMsg();
}
