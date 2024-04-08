package QuizApp.advice;

import QuizApp.exceptions.BadRequestException;
import QuizApp.messages.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;


@RestControllerAdvice
public class ExceptionAdvice {
    ErrorMessage errorMessage;

    @Autowired
    public ExceptionAdvice(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handleNoSuchElementException(NoSuchElementException exp){
        errorMessage.setMessage(exp.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadClientRequest(BadRequestException exp){
        errorMessage.setMessage(Objects.requireNonNull(exp.getBindingResult().getFieldError()).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleHibernateException(RuntimeException exp){
        errorMessage.setMessage(exp.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error  ->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }
}
