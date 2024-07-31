package dev.patika.Library.Management.System.core.config;

import dev.patika.Library.Management.System.core.exception.NotFoundException;
import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.core.utillies.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

public class GlobalExceptionHandling {
    // NotFoundException türündeki istisnaları yakalar ve uygun HTTP yanıtı döner
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    // MethodArgumentNotValidException türündeki istisnaları yakalar
    //Boş alanlar ve gerçersiz formatlar
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        // Hata mesajlarını toplar ve bir listeye dönüştürür
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        // Hata listesini içeren bir yanıt döner
        return new ResponseEntity<>(ResultHelper.validateEroor(validationErrorList), HttpStatus.BAD_REQUEST);

    }
}
