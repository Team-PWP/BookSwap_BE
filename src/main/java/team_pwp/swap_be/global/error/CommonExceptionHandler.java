package team_pwp.swap_be.global.error;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "team_pwp.swap_be")
@Slf4j
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> badDateFormatExceptionHandler(DateTimeParseException e) {
        log.error("DateTimeParseException : {}", "시간 형식이 맞지 않습니다. YYYY-MM-DDTHH:MM:SS의 형식을 맞춰주세요");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                " 시간 형식이 맞지 않습니다. YYYY-MM-DDTHH:MM:SS의 형식을 맞춰주세요"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.error("ValidationException : {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }
}
