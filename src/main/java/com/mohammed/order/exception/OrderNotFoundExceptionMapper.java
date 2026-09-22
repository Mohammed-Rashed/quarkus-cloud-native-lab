package com.mohammed.order.exception;

import com.mohammed.order.dto.ErrorResponseDto;
import com.mohammed.order.dto.ValidationErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class OrderNotFoundExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getConstraintViolations()
                .forEach(violation -> {


                    String path = violation.getPropertyPath().toString();

                    String field = path.substring(
                            path.lastIndexOf('.') + 1
                    );
                    String message = violation.getMessage();

                    errors.put(field, message);
                });

        ValidationErrorResponseDto response =
                new ValidationErrorResponseDto(
                        400,
                        "Validation failed",
                        errors
                );

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}
