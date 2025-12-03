package org.bf.global.infrastructure.error;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getStatus();
    String getCode();
    String getMessage();

}
