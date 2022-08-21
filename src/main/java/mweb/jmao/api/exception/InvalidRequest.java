package mweb.jmao.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * status -> 400
 */
@Getter
public class InvalidRequest extends MwebException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String message;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int statusCode() {
        return 400;
    }

}
