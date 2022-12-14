package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public UnAuthorizedException(String message) {
        super(ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, message);
    }

}
