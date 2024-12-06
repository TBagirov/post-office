package org.bagirov.postoffice.service

import org.bagirov.postoffice.dto.response.ReportResponse.ReportPublicationResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriberResponse
import org.bagirov.postoffice.dto.response.ReportResponse.ReportSubscriptionResponse
import org.bagirov.postoffice.repository.PublicationRepository
import org.bagirov.postoffice.repository.SubscriptionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ReportService (
    private val subscriptionRepository: SubscriptionRepository,
    private val publicationRepository: PublicationRepository
){
    fun subscriberReport(): List<ReportSubscriberResponse>{
        val temp = subscriptionRepository.findAll()

        val report: MutableList<ReportSubscriberResponse> = mutableListOf()
        for (sub in temp) {
            val endDate: LocalDateTime = sub.startDate.plusMonths(sub.duration.toLong())

            report.add(ReportSubscriberResponse(
                fioSubscriber = sub.subscriber!!.getFio(),
                titlePublication = sub.publication!!.title,
                startDateSubscription = sub.startDate,
                endDateSubscription = endDate,
                statusSubscription = if (LocalDateTime.now() < endDate) "Активна" else "Законченна"
            ))
        }

        return report
    }

    fun publicationReport(): List<ReportPublicationResponse> {
        val temp = publicationRepository.findAll()

        val report: MutableList<ReportPublicationResponse> = mutableListOf()
        for (pub in temp) {

            report.add(ReportPublicationResponse(
                title = pub.title,
                type = pub.publicationType.type,
                price = pub.price,
                countSubscriber = pub.subscriptions!!.count()
            ))
        }

        return report
    }

    fun subscriptionReport(subscriberId: UUID): List<ReportSubscriptionResponse>{
        val temp = subscriptionRepository.findAll()

        val report: MutableList<ReportSubscriptionResponse> = mutableListOf()
        for (sub in temp) {
            val endDate: LocalDateTime = sub.startDate.plusMonths(sub.duration.toLong())
            if(sub.subscriber!!.id  == subscriberId)
                report.add(ReportSubscriptionResponse(
                    title = sub.publication!!.title,
                    type = sub.publication!!.publicationType.type,
                    startDate = sub.startDate,
                    endDate = endDate,
                    price = sub.publication!!.price
                ))

        }

        return report
    }
}