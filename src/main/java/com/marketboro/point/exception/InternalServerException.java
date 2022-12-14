package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class InternalServerException extends CustomException {
    public InternalServerException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public InternalServerException(String message) {
        super(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, message);
    }
}
