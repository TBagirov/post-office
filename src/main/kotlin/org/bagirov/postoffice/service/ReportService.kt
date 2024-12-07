package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.response.ReportResponse.ReportPublicationResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriptionResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriptionByIdSubscriberResponse
import org.bagirov.postoffice.repository.PublicationRepository
import org.bagirov.postoffice.repository.SubscriptionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReportService (
    private val subscriptionRepository: SubscriptionRepository,
    private val publicationRepository: PublicationRepository
){
    fun subscriptionReport(): List<ReportSubscriptionResponse>{
//        val temp = subscriptionRepository.findAll()
//
//        val report: MutableList<ReportSubscriberResponse> = mutableListOf()
//
//        for (sub in temp) {
//            val endDate: LocalDateTime = sub.startDate.plusMonths(sub.duration.toLong())
//
//            report.add(ReportSubscriberResponse(
//                fioSubscriber = sub.subscriber!!.getFio(),
//                titlePublication = sub.publication?.title,
//                startDateSubscription = sub.startDate,
//                endDateSubscription = endDate,
//                statusSubscription = if (LocalDateTime.now() < endDate) "Активна" else "Законченна"
//            ))
//        }

        val report = subscriptionRepository.generateReport()

        val reportResponse = (report.map { it -> ReportSubscriptionResponse(
            subscriptionId = it.subscriptionId,
            subscriberId = it.subscriberId,
            publicationId = it.publicationId,
            fioSubscriber = it.fioSubscriber,
            titlePublication = it.titlePublication,
            startDateSubscription = it.startDateSubscription,
            endDateSubscription = it.endDateSubscription,
            statusSubscription = it.statusSubscription
        )})


        return reportResponse
    }

    fun publicationReport(): List<ReportPublicationResponse> {
//        val temp = publicationRepository.findAll()
//
//        val report: MutableList<ReportPublicationResponse> = mutableListOf()
//        for (pub in temp) {
//
//            report.add(ReportPublicationResponse(
//                title = pub.title,
//                type = pub.publicationType?.type,
//                price = pub.price,
//                countSubscriber = pub.subscriptions!!.count()
//            ))
//        }

        val report = publicationRepository.generateReport()

        val reportResponse = (report.map { it -> ReportPublicationResponse(
            publicationId = it.publicationId,
            title = it.title,
            type = it.type,
            price = it.price,
            countSubscriber = it.countSubscriber
        )})


        return reportResponse
    }

    fun subscriptionByIdSubscriberReport(subscriberId: UUID): List<ReportSubscriptionByIdSubscriberResponse>{
//        val temp = subscriptionRepository.findAll()
//
//        val report: MutableList<ReportSubscriptionByIdSubscriberResponse> = mutableListOf()
//        for (sub in temp) {
//            val endDate: LocalDateTime = sub.startDate.plusMonths(sub.duration.toLong())
//            if(sub.subscriber!!.id  == subscriberId)
//                report.add(ReportSubscriptionByIdSubscriberResponse(
//                    title = sub.publication?.title,
//                    type = sub.publication?.publicationType?.type,
//                    startDate = sub.startDate,
//                    endDate = endDate,
//                    price = sub.publication?.price
//                ))
//
//        }


        val report = subscriptionRepository.generateReportBySubscriberId(subscriberId)

        val reportResponse = (report.map { it -> ReportSubscriptionByIdSubscriberResponse(
            subscriptionId = it.subscriptionId,
            publicationId = it.publicationId,
            title = it.title,
            type = it.type,
            price = it.price,
            startDate = it.startDate,
            endDate = it.endDate,
        )})


        return reportResponse
    }



}