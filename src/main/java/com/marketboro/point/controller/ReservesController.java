package com.marketboro.point.controller;

import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.service.ReservesService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReservesController {

    private final ReservesService reservesService;

    // 1. 적립금 적립
    @PostMapping("/reserves")
    public void savedReserves(@RequestBody @Valid SaveReservesReq saveReservesReq, BindingResult bindingResult) {

    }

    // 2. 적릭금 사용

    // 3. 적립금 합계 조회

    // 4. 적립/사용 내역 조회

}
