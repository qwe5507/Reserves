package com.marketboro.point.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

@Getter
public class SaveReservesReq {
    @NotNull
    private String memberId;

    @NotNull
    private BigDecimal amount;

}
