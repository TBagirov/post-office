package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.PublicationTypeEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface PublicationTypeRepository: JpaRepository<PublicationTypeEntity, UUID> {


    @Query("""
        SELECT
            *
        FROM
            publication_types 
        WHERE
            name = :type
    """, nativeQuery = true)
    fun findByType(@Param("type")type: String): PublicationTypeEntity?

    @Query("""
        SELECT
            *
        FROM
            publication_types 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<PublicationTypeEntity>

    @Query("""
        SELECT
            *
        FROM
            publication_types  
        """, nativeQuery = true)
    override fun findAll(): MutableList<PublicationTypeEntity>


    @Transactional
    @Query(
        """
        INSERT INTO publication_types (id, name)
        VALUES (:id ,:name)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun savePublicationType(
        @Param("id") id: UUID,
        @Param("name") name: String,
    ): PublicationTypeEntity


    @Transactional
    @Query(
        """
    UPDATE publication_types
    SET name = :name
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updatePublicationType(
        @Param("id") id: UUID,
        @Param("name") name: String,
    ): PublicationTypeEntity


    @Modifying
    @Query("""
        DELETE FROM publication_types WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)
}