package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface PostmanRepository: JpaRepository<PostmanEntity, UUID> {

    @Query("""
        SELECT
            *
        FROM
            postmans 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<PostmanEntity>

    @Query("""
        SELECT
            *
        FROM
            postmans  
        """, nativeQuery = true)
    override fun findAll(): MutableList<PostmanEntity>


    @Transactional
    @Query(
        """
        INSERT INTO postmans (id, name, surname, patronymic)
        VALUES (:id ,:name, :surname,:patronymic)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun savePostman(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("surname") surname: String,
        @Param("patronymic") patronymic: String,
    ): PostmanEntity


    @Transactional
    @Query(
        """
    UPDATE postmans
    SET name = :name,
        surname = :surname,
        patronymic = :patronymic
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updatePostman(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("surname") surname: String,
        @Param("patronymic") patronymic: String,
    ): PostmanEntity

    @Modifying
    @Query("""
        DELETE FROM postmans WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)
}