package org.bagirov.postoffice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.service.ReportService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@CrossOrigin(origins = arrayOf("http://localhost:3000"))
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
    fun getReportSubscriptions(): ResponseEntity<ByteArray> {
        val excelFile: ByteArray? = reportService.subscriptionReport()
        val headers: HttpHeaders = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM)

        val nameFile = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd__HH.mm"))
        val typeFile = ".xlsx"

        headers.setContentDispositionFormData("attachment", nameFile + typeFile)
        return ResponseEntity<ByteArray>(excelFile, headers, HttpStatus.OK)
    }

    @GetMapping("/publications")
    @Operation(
        summary = "Получение отчета по изданиям",
        description = "Получение отчета с данными о издания"
    )
    fun getReportPublications(): ResponseEntity<ByteArray> {
        val excelFile: ByteArray? = reportService.publicationReport()
        val headers: HttpHeaders = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM)

        val nameFile = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd__HH.mm"))
        val typeFile = ".xlsx"

        headers.setContentDispositionFormData("attachment", nameFile + typeFile)
        return ResponseEntity<ByteArray>(excelFile, headers, HttpStatus.OK)
    }

    @GetMapping("/subscriber-subscriptions")
    @Operation(
        summary = "Получение отчета о подписках подписчика по id",
        description = "Получение отчета c данными о подписках подписчика по id"
    )
    fun getReportSubscriptionsBySubscriberId(@RequestParam id: UUID): ResponseEntity<ByteArray> {

        val excelFile: ByteArray? = reportService.subscriptionByIdSubscriberReport(id)
        val headers: HttpHeaders = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM)

        val nameFile = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd__HH.mm"))
        val typeFile = ".xlsx"

        headers.setContentDispositionFormData("attachment", nameFile + typeFile)
        return ResponseEntity<ByteArray>(excelFile, headers, HttpStatus.OK)
    }

}
