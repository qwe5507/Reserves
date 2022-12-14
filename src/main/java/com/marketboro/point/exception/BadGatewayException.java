package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class BadGatewayException extends CustomException {

    public BadGatewayException(String message) {
        super(ErrorCode.E502_BAD_GATEWAY, ErrorAction.NONE, message);
    }

    public BadGatewayException(ErrorCode errorCode, ErrorAction action, String message){
        super(errorCode, action, message);
    }
}
