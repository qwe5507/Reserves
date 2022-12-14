package com.marketboro.point.controller;

import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;
import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.exception.InvalidException;
import com.marketboro.point.service.ReservesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReservesController {

    private final ReservesService reservesService;

    // 1. 적립금 적립
    @PostMapping("/reserves")
    public void savedReserves(@RequestBody @Valid SaveReservesReq saveReservesReq) {
        reservesService.savedReserves(saveReservesReq);
    }

    // 2. 적립금 합계 조회

    // 3. 적립/사용 내역 조회

    // 4. 적릭금 사용

}
