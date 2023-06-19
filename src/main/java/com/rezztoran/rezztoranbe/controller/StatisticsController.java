package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.StatisticDto;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Statistics controller. */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

  private final StatisticsService statisticsService;
  private final ModelMapper modelMapper;

  @GetMapping("/count")
  public ResponseEntity<ApiResponse<Object>> getStatistics() {
    var response = modelMapper
        .map(statisticsService.getTotalCounts(), StatisticDto.class);
    return ApiResponse.builder().data(response).build();
  }
}
