package org.bagirov.postoffice.utill

import org.bagirov.postoffice.dto.request.PublicationTypeRequest
import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.response.*
import org.bagirov.postoffice.entity.*

fun PostmanEntity.convertToResponseDto() = PostmanResponse(
    id = this.id!!,
    name = this.user.name,
    surname = this.user.surname,
    patronymic = this.user.patronymic,
    regions = this.districts?.map { it -> it.region?.name }
)

fun StreetEntity.convertToResponseDto() = StreetResponse(
    id = this.id!!,
    name = this.name,
    regionName = region?.name,
)

fun StreetRequest.convertToEntity() = StreetEntity(
    id = null,
    name = this.name,
    region = null
)

fun RegionEntity.convertToResponseDto() = RegionResponse(
    id = this.id!!,
    name = this.name,
    streets = this.streets?.map { it -> it.name },
    postmans = this.districts?.map { it -> it.postman?.user?.getFio() }
)


fun DistrictEntity.convertToResponseDto() = DistrictResponse(
    id = this.id!!,
    postmanName = this.postman?.user?.getFio(),
    regionName = this.region?.name
)

fun PublicationTypeRequest.convertToEntity() = PublicationTypeEntity(
    id = null,
    type = this.type,
    publications = null
)

fun PublicationEntity.convertToResponseDto() = PublicationResponse(
    id = this.id!!,
    index = this.index,
    title = this.title,
    publicationType = this.publicationType?.type,
    price = this.price
)

fun PublicationTypeEntity.convertToResponseDto() = PublicationTypeResponse(
    id = this.id!!,
    type = this.type,
    publications = this.publications?.map { it -> it.convertToResponseDto() }
)

fun SubscriberEntity.convertToResponseDto() = SubscriberResponse(
    id = this.id!!,
    name = this.user.name,
    surname = this.user.surname,
    patronymic = this.user.patronymic,
    building = this.building,
    subAddress = this.subAddress,
    postmanName = this.district?.postman?.user?.getFio(),
    regionName = this.district?.region?.name,
    street = this.street?.name
)

fun SubscriptionEntity.convertToResponseDto() = SubscriptionResponse(
    id = this.id!!,
    subscriber = this.subscriber?.convertToResponseDto(),
    publication = this. publication?.convertToResponseDto(),
    startDate = this.startDate,
    duration = this.duration,
    price = if(this.publication?.price != null) this.publication!!.price * duration else null
)

fun RoleEntity.convertToResponseDto() = RoleResponse(
    id = this.id!!,
    name = this.name
)
