package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class ConflictException extends CustomException {

    public ConflictException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ConflictException(String message) {
        super( ErrorCode.E409_CONFLICT_EXCEPTION, ErrorAction.NONE, message);
    }

}
