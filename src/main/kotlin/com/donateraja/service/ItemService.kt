package com.donateraja.service

import com.donateraja.domain.admin.ItemResponseDTO
import com.donateraja.domain.item.ItemCreateDTO
import com.donateraja.domain.item.ItemStatusDTO
import com.donateraja.domain.item.ItemUpdateDTO
import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.DonationOrRent

interface ItemService {
    fun postItem(itemCreateDTO: ItemCreateDTO): ItemResponseDTO
    fun getItem(itemId: Long): ItemResponseDTO
    fun updateItem(itemId: Long, itemUpdateDTO: ItemUpdateDTO): ItemResponseDTO
    fun deleteItem(itemId: Long)
    fun updateItemStatus(itemId: Long, itemStatusDTO: ItemStatusDTO): ItemResponseDTO
    fun searchItemsByPincode(pincode: String, category: Category?, donationOrRent: DonationOrRent?): List<ItemResponseDTO>
    fun getAllItems(category: Category?, donationOrRent: DonationOrRent?, location: String?): List<ItemResponseDTO>
    fun searchItems(query: String, category: Category?, donationOrRent: DonationOrRent?): List<ItemResponseDTO>
}
