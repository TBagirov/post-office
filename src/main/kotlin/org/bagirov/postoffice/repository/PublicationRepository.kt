package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface PublicationRepository: JpaRepository<PublicationEntity, UUID> {

    @Query("""
        SELECT
            *
        FROM
            publications 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<PublicationEntity>

    @Query("""
        SELECT
            *
        FROM
            publications  
        """, nativeQuery = true)
    override fun findAll(): MutableList<PublicationEntity>


    @Transactional
    @Query(
        """
        INSERT INTO publications (id, index, title, publication_type_id, price)
        VALUES (:id ,:index, :title, :publication_type_id, :price)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun savePublication(
        @Param("id") id: UUID,
        @Param("index") index: String,
        @Param("title") title: String,
        @Param("publication_type_id") publicationTypeId: UUID,
        @Param("price") price: Int
    ): PublicationEntity


    @Transactional
    @Query(
        """
    UPDATE publications
    SET index = :index,
        title = :title, 
        publication_type_id = :publication_type_id,
        price = :price
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updatePublication(
        @Param("id") id: UUID,
        @Param("index") index: String,
        @Param("title") title: String,
        @Param("publication_type_id") publicationTypeId: UUID?,
        @Param("price") price: Int
    ): PublicationEntity

    @Modifying
    @Query("""
        DELETE FROM publications WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)
}