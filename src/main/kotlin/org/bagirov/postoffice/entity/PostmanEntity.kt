package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "postmans")
data class PostmanEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name="name", nullable = false)
    var name: String,

    @Column(name="surname", nullable = false)
    var surname: String,

    @Column(name="patronymic", nullable = false)
    var patronymic: String,

    @OneToMany(mappedBy = "postman")
    var districts: MutableList<DistrictEntity>? = null

){
    fun getFio() = listOfNotNull(surname, name, patronymic)
        .joinToString(" ")
}
