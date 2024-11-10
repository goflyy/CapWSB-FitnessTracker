package com.capgemini.wsb.fitnesstracker.mail.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final MonthlyReportScheduler monthlyReportScheduler;

    @GetMapping("/generate-report")
    public String generateReport() {
        monthlyReportScheduler.generateAndSendMonthlyReports();
        return "Report generation triggered.";
    }
}
