package ws.l10n.api;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ws.l10n.common.logging.LoggerUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerUtils.getLogger();

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorResponse httpRequestMethodExceptionHandler(HttpRequestMethodNotSupportedException e) {
        LOGGER.debug(e.getMessage(), e);
        return new ErrorResponse(ApiResponseStatus.BAD_HTTP_REQUEST, "Method not supported.");
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse servletRequestParameterException(MissingServletRequestParameterException e) {
        LOGGER.debug(e.getMessage(), e);
        return new ErrorResponse(ApiResponseStatus.MISSING_PARAMETER, e.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onResourceNotFoundException(ResourceNotFoundException e) {
        LOGGER.debug(e.getMessage(), e);
        return new ErrorResponse(ApiResponseStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorResponse defaultExceptionHandler(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(ApiResponseStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoHandlerFoundException(Exception e) {
        LOGGER.error(e.getMessage());
        return new ErrorResponse(ApiResponseStatus.NOT_FOUND, "No resource found!");
    }

}