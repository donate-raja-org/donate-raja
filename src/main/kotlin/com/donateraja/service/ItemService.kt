package com.donateraja.service

import com.donateraja.entity.Item
import java.util.*

interface ItemService {

    fun createItem(item: Item): Item

    fun getAllItems(category: String?, donationOrRent: String?, location: String?): List<Item>

    fun getItemById(itemId: Long): Optional<Item>

    fun updateItem(itemId: Long, itemDetails: Item): Optional<Item>

    fun deleteItem(itemId: Long): Boolean

    fun getItemsByUser(userId: Long): List<Item>

    fun searchItems(query: String, category: String?, donationOrRent: String?): List<Item>
}
