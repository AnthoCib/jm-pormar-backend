package com.jmpormar.dashboard.controller;

import com.jmpormar.dashboard.dto.DashboardResponse;
import com.jmpormar.dashboard.service.DashboardService;
import com.jmpormar.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService service;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> obtener() {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener()));
    }
}
