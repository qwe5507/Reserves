package com.marketboro.point.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter @AllArgsConstructor
public class SaveReservesReq {
    @NotNull(message = "memberId는 필수 값 입니다.")
    @JsonProperty(value = "member_id")
    private String memberId;

    @NotNull(message = "amount는 필수 값 입니다.")
    private Long amount;

}
