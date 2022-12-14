package com.marketboro.point.exception;


import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;

public class ServiceUnAvailableException extends CustomException {
    public ServiceUnAvailableException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ServiceUnAvailableException(String message) {
        super(ErrorCode.E503_SERVICE_UNAVAILABLE, ErrorAction.NONE, message);
    }

}
