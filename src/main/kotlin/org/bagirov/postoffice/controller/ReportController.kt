package org.bagirov.postoffice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.response.ReportResponse.ReportPublicationResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriptionResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriptionByIdSubscriberResponse
import org.bagirov.postoffice.service.ReportService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/report")
@Tag(name = "ReportController", description = "Контроллер для взаимодействия с отчетами")
class ReportController (
    private val reportService: ReportService
){

    @GetMapping("/subscriptions")
    @Operation(
        summary = "Получение отчета по подпискам",
        description = "Получение отчета с данными о подписках"
    )
    fun getReportSubscriptions(): ResponseEntity<List<ReportSubscriptionResponse>> {
        return ResponseEntity.ok(reportService.subscriptionReport())
    }

    @GetMapping("/publications")
    @Operation(
        summary = "Получение отчета по изданиям",
        description = "Получение отчета с данными о издания"
    )
    fun getReportPublications(): ResponseEntity<List<ReportPublicationResponse>> {
        return ResponseEntity.ok(reportService.publicationReport())
    }

    @GetMapping("/subscriber-subscriptions")
    @Operation(
        summary = "Получение отчета о подписках подписчика по id",
        description = "Получение отчета c данными о подписках подписчика по id"
    )
    fun getReportSubscriptionsBySubscriberId(@RequestParam id: UUID): ResponseEntity<List<ReportSubscriptionByIdSubscriberResponse>> {
        return ResponseEntity.ok(reportService.subscriptionByIdSubscriberReport(id))
    }

}
