package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class InvalidException extends CustomException {

    public InvalidException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public InvalidException(String message) {
        super(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, message);
    }

}
