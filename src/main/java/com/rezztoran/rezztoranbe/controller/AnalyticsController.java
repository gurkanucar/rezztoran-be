package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.AnalyticsDTO;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Analytics controller. */
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Slf4j
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final ModelMapper modelMapper;


    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Object>> getCountRestaurant() {

        var response = modelMapper.map(analyticsService.getTotalCounts(), AnalyticsDTO.class);
        return ApiResponse.builder().data(response).build();
    }
}



