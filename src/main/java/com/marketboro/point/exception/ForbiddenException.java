package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class ForbiddenException extends CustomException {

    public ForbiddenException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ForbiddenException(String message) {
        super(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.NONE, message);
    }

}
