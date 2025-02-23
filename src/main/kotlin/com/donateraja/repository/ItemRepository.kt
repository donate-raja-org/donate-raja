package com.donateraja.repository

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.DonationOrRent
import com.donateraja.entity.item.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    @Query(
        """
        SELECT i FROM Item i 
        WHERE i.pincode = :pincode 
        AND (:category IS NULL OR i.category = :category) 
        AND (:donationOrRent IS NULL OR i.donationOrRent = :donationOrRent)
    """
    )
    fun findByPincodeAndFilters(
        @Param("pincode") pincode: String,
        @Param("category") category: Category?,
        @Param("donationOrRent") donationOrRent: DonationOrRent?
    ): List<Item>

    @Query(
        """
        SELECT i FROM Item i 
        WHERE (:category IS NULL OR i.category = :category) 
        AND (:donationOrRent IS NULL OR i.donationOrRent = :donationOrRent) 
        AND (:location IS NULL OR i.location ILIKE CONCAT('%', :location, '%'))
    """
    )
    fun findAllByFilters(
        @Param("category") category: Category?,
        @Param("donationOrRent") donationOrRent: DonationOrRent?,
        @Param("location") location: String?
    ): List<Item>

    @Query(
        """
        SELECT i FROM Item i 
        WHERE (:category IS NULL OR i.category = :category) 
        AND (:donationOrRent IS NULL OR i.donationOrRent = :donationOrRent) 
        AND (
            i.itemName ILIKE :query 
            OR i.description ILIKE :query
        )
    """
    )
    fun searchByQuery(
        @Param("query") query: String,
        @Param("category") category: Category?,
        @Param("donationOrRent") donationOrRent: DonationOrRent?
    ): List<Item>

    @Query("SELECT i FROM Item i WHERE (:status IS NULL OR i.status = :status) AND (:type IS NULL OR i.donationOrRent = :type)")
    fun findItemsByStatusAndType(@Param("status") status: String?, @Param("type") type: String?): List<Item>
}
