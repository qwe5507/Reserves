package com.marketboro.point.dto.response;

import com.marketboro.point.dto.enums.ErrorAction;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private String code;
    private ErrorAction action;
    private String message;
}