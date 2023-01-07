package com.marketboro.point.controller;

import com.marketboro.point.domain.history.projection.HistoryProjection;
import com.marketboro.point.dto.request.CancelReq;
import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.dto.request.UseReservesReq;
import com.marketboro.point.dto.response.ApiResponse;
import com.marketboro.point.service.ReservesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReservesController {

    private final ReservesService reservesService;

    // 1. 적립금 적립
    @PostMapping("/reserves")
    public ApiResponse savedReserves(@RequestBody @Valid SaveReservesReq saveReservesReq) {
        reservesService.savedReserves(saveReservesReq);

        return ApiResponse.SUCCESS;
    }

    // 2. 적립금 합계 조회
    @GetMapping("/reserves-total")
    public ApiResponse<Long> getReservesTotal(String memberId) {
        return ApiResponse.success(reservesService.getReservesTotal(memberId));
    }

    // 3. 적릭금 사용
    @PutMapping("/reserves")
    public ApiResponse useReserves(@RequestBody @Valid UseReservesReq useReservesReq) {
            reservesService.useReserves(useReservesReq);

        return ApiResponse.SUCCESS;
    }

    // 4. 적립/사용 내역 조회
    @GetMapping("/history")
    public ApiResponse<Page<HistoryProjection>> getReservesList(String memberId,
                                                                @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(reservesService.getReservesList(memberId, pageable));
    }

    // 5. 사용 취소
    @PostMapping("/cancel-reserves")
    public ApiResponse cancelReserves(@RequestBody @Valid CancelReq cancelReq) {
        reservesService.cancel(cancelReq);
        return ApiResponse.SUCCESS;
    }
}
