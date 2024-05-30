package com.indocyber.winterhold.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorMessageDto<T> {
    private  final HttpStatus httpStatus;
    private  final String message;
    private final T errors;
    private final ZonedDateTime timesStamp = ZonedDateTime.now(ZoneId.of("Z"));
    private  final Throwable throwable;
}
