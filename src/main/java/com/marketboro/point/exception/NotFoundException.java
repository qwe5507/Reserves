package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public NotFoundException(String message) {
        super(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.NONE, message);
    }

}
