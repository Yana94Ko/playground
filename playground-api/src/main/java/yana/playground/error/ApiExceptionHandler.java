package yana.playground.error;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yana.playground.member.exceptions.MemberNotFoundException;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    /*
     *  @Validated로 Service에서 binding error 발생시 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.VALIDATION_ERROR, request);
    }
    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException( MethodArgumentTypeMismatchException e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.BAD_REQUEST, request);
    }
    @ExceptionHandler(MemberNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleMemberNotFoundException(MemberNotFoundException e, WebRequest request) {
        ErrorCode errorCode = ErrorCode.MEMBER_NOT_FOUND;
        String errorMessage = String.format(errorCode.getMessage(), e.getMemberId());
        ApiErrorResponse errorResponse = new ApiErrorResponse(false, errorCode.getCode(), errorMessage);
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
    @ExceptionHandler
    protected ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_ERROR, request);
    }

    /*
     *  @Valid, @Validated로 Controller에서 binding error 발생시 처리
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if( e instanceof MethodArgumentNotValidException){
            return handleExceptionInternal(e, ErrorCode.VALIDATION_ERROR, request);
        }
        return super.handleExceptionInternal(e, body, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                headers,
                status,
                request
        );
    }

}