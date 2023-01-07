package com.marketboro.point.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CancelReq {
    @JsonProperty(value = "history_id")
    @NotNull
    @NotNull(message = "historyId는 필수 값 입니다.")
    private Long historyId;
}
