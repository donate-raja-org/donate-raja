package com.donateraja.service.impl

import com.donateraja.entity.item.Item
import com.donateraja.repository.ItemRepository
import com.donateraja.service.ItemService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemServiceImpl(private val itemRepository: ItemRepository) : ItemService {

    override fun createItem(item: Item): Item {
        return itemRepository.save(item)
    }

    override fun getAllItems(category: String?, donationOrRent: String?, location: String?): List<Item> {
        return itemRepository.findByFilters(category, donationOrRent, location)
    }

    override fun getItemById(itemId: Long): Optional<Item> {
        return itemRepository.findById(itemId)
    }

    override fun updateItem(itemId: Long, itemDetails: Item): Optional<Item> {
        return itemRepository.findById(itemId).map { item ->
            item.apply {
                itemName = itemDetails.itemName
                description = itemDetails.description
                condition = itemDetails.condition
                price = itemDetails.price
                location = itemDetails.location
                category = itemDetails.category
            }
            itemRepository.save(item)
        }
    }

    override fun deleteItem(itemId: Long): Boolean {
        return if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId)
            true
        } else {
            false
        }
    }

    override fun getItemsByUser(userId: Long): List<Item> {
        return itemRepository.findByUserId(userId)
    }

    override fun searchItems(query: String, category: String?, donationOrRent: String?): List<Item> {
        return itemRepository.findItems(query, category, donationOrRent)
    }
}
