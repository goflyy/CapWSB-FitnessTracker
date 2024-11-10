package com.capgemini.wsb.fitnesstracker.mail.api;

import com.capgemini.wsb.fitnesstracker.mail.internal.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MonthlyReportScheduler {

    
    private final ReportService reportService;

    @Scheduled(cron = "0 0 1 1 * ?") // Każdego 1 dnia miesiąca o 01:00
    public void generateAndSendMonthlyReports() {
        log.info("Starting monthly report generation...");
        reportService.generateAndSendReports();
        log.info("Monthly report generation completed.");
    }
}
