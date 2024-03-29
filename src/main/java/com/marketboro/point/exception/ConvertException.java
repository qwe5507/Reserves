package com.marketboro.point.exception;

import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class ConvertException extends RuntimeException {

    private ErrorCode errorCode;

    private ErrorAction action;

    public ConvertException(ErrorCode errorCode, ErrorAction action, String message) {
        super(message);
        this.errorCode = errorCode;
        this.action = action;
    }

    public ConvertException(String message){
        super(message);
        this.errorCode = ErrorCode.E470_CONVERT_EXCEPTION;
        this.action = ErrorAction.NONE;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorAction getAction() { return action; }

}
