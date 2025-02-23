package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.admin.ItemResponseDTO
import com.donateraja.domain.item.ItemCreateDTO
import com.donateraja.domain.item.ItemStatusDTO
import com.donateraja.domain.item.ItemUpdateDTO
import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.DonationOrRent
import com.donateraja.entity.item.Item
import com.donateraja.entity.item.ItemImage
import com.donateraja.entity.item.ItemTag
import com.donateraja.entity.user.User
import com.donateraja.repository.ItemImageRepository
import com.donateraja.repository.ItemRepository
import com.donateraja.repository.TagRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository,
    private val itemImageRepository: ItemImageRepository,
    private val tagRepository: TagRepository
) : ItemService {

    override fun postItem(itemCreateDTO: ItemCreateDTO): ItemResponseDTO {
        val user = getUserById(itemCreateDTO.userId)
        val item = createItemEntity(itemCreateDTO, user)
        val savedItem = itemRepository.save(item)

        saveItemImages(savedItem, itemCreateDTO.imageUrls)
        saveItemTags(savedItem, itemCreateDTO.tags)

        return mapToResponseDTO(savedItem)
    }

    override fun getItem(itemId: Long): ItemResponseDTO {
        val item = itemRepository.findById(itemId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Item not found") }
        return mapToResponseDTO(item)
    }

    override fun updateItem(itemId: Long, itemUpdateDTO: ItemUpdateDTO): ItemResponseDTO {
        val item = getItemEntity(itemId)

        itemUpdateDTO.itemName?.let { item.itemName = it }
        itemUpdateDTO.description?.let { item.description = it }
        itemUpdateDTO.price?.let { item.price = it }
        item.updatedAt = LocalDateTime.now()

        val updatedItem = itemRepository.save(item)
        return mapToResponseDTO(updatedItem)
    }

    override fun deleteItem(itemId: Long) {
        if (!itemRepository.existsById(itemId)) {
            throw ServiceException(HttpStatus.NOT_FOUND, "Item not found")
        }
        itemImageRepository.deleteByItemId(itemId)
        tagRepository.deleteByItemId(itemId)
        itemRepository.deleteById(itemId)
    }

    override fun updateItemStatus(itemId: Long, itemStatusDTO: ItemStatusDTO): ItemResponseDTO {
        val item = getItemEntity(itemId)
        item.status = itemStatusDTO.status ?: throw ServiceException(HttpStatus.BAD_REQUEST, "Status is required")
        item.updatedAt = LocalDateTime.now()
        return mapToResponseDTO(itemRepository.save(item))
    }

    override fun searchItemsByPincode(
        pincode: String,
        category: Category?,
        donationOrRent: DonationOrRent?
    ): List<ItemResponseDTO> = itemRepository.findByPincodeAndFilters(pincode, category, donationOrRent)
        .map { mapToResponseDTO(it) }

    override fun getAllItems(category: Category?, donationOrRent: DonationOrRent?, location: String?): List<ItemResponseDTO> =
        itemRepository.findAllByFilters(category, donationOrRent, location)
            .map { mapToResponseDTO(it) }

    override fun searchItems(query: String, category: Category?, donationOrRent: DonationOrRent?): List<ItemResponseDTO> =
        itemRepository.searchByQuery("%$query%", category, donationOrRent)
            .map { mapToResponseDTO(it) }

    private fun getUserById(userId: Long?): User =
        userRepository.findById(userId ?: throw ServiceException(HttpStatus.BAD_REQUEST, "User ID required"))
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "User not found") }

    private fun createItemEntity(dto: ItemCreateDTO, user: User): Item = Item(
        itemName = requireNotNull(dto.itemName) { "Item name required" },
        description = requireNotNull(dto.description) { "Description required" },
        condition = requireNotNull(dto.condition) { "Condition required" },
        price = requireNotNull(dto.price) { "Price required" },
        location = requireNotNull(dto.location) { "Location required" },
        pincode = requireNotNull(dto.pincode) { "Pincode required" },
        category = requireNotNull(dto.category) { "Category required" },
        donationOrRent = requireNotNull(dto.donationOrRent) { "Donation/Rent type required" },
        user = user
    )

    private fun saveItemImages(item: Item, imageUrls: List<String>) {
        imageUrls.forEach { url ->
            itemImageRepository.save(ItemImage(item = item, imageUrl = url))
        }
    }

    private fun saveItemTags(item: Item, tags: List<String>) {
        tags.forEach { tagName ->
            val cleanedTag = tagName.trim()
            if (cleanedTag.isNotEmpty()) {
                tagRepository.save(
                    ItemTag(
                        item = item,
                        tag = cleanedTag,
                        createdAt = LocalDateTime.now() // Add createdAt
                    )
                )
            }
        }
    }

    private fun getItemEntity(itemId: Long): Item = itemRepository.findById(itemId)
        .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Item not found") }

    private fun mapToResponseDTO(item: Item): ItemResponseDTO = ItemResponseDTO(
        id = item.id,
        itemName = item.itemName,
        description = item.description,
        condition = item.condition,
        price = item.price,
        location = item.location,
        pincode = item.pincode,
        category = item.category,
        donationOrRent = item.donationOrRent,
        status = item.status,
        createdAt = item.createdAt,
        updatedAt = item.updatedAt,
        images = itemImageRepository.findByItemId(item.id).map { it.imageUrl },
        tags = tagRepository.findByItemId(item.id).map { it.tag }
    )
}
