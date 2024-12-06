package org.bagirov.postoffice.controller

import org.bagirov.postoffice.dto.response.ReportResponse.ReportPublicationResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriberResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriptionResponse
import org.bagirov.postoffice.service.ReportService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/report")
class ReportController (
    private val reportService: ReportService
){

    @GetMapping("/subscribers")
    fun getReportSubscribers(): ResponseEntity<List<ReportSubscriberResponse>> {
        return ResponseEntity.ok(reportService.subscriberReport())
    }

    @GetMapping("/publications")
    fun getReportPublications(): ResponseEntity<List<ReportPublicationResponse>> {
        return ResponseEntity.ok(reportService.publicationReport())
    }

    @GetMapping("/subscriptions")
    fun getReportSubscriptions(@RequestParam id: UUID): ResponseEntity<List<ReportSubscriptionResponse>> {
        return ResponseEntity.ok(reportService.subscriptionReport(id))
    }

}
