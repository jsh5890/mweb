package mweb.jmao.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MwebException extends RuntimeException{

    public final Map<String, String> vaildation = new HashMap<>();

    public MwebException(String message) {
        super(message);
    }

    public MwebException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message){
        vaildation.put(fieldName, message);
    }
}
