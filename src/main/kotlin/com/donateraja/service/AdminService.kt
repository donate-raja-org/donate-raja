package com.donateraja.service

import com.donateraja.domain.admin.ItemApprovalDTO
import com.donateraja.domain.admin.ItemResponseDTO
import com.donateraja.domain.admin.UserProfileDto
import com.donateraja.domain.user.UserStatusDTO
import com.donateraja.entity.constants.UserStatus
import com.donateraja.entity.user.UserRole
import com.donateraja.repository.ItemImageRepository
import com.donateraja.repository.ItemRepository
import com.donateraja.repository.TagRepository
import com.donateraja.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository,
    private val itemImageRepository: ItemImageRepository,
    private val tagRepository: TagRepository
) {

    fun listUsers(status: UserStatus?, role: UserRole?): List<UserProfileDto> =
        userRepository.findUsersByStatusAndRole(status, role).map {
            UserProfileDto.fromEntity(it)
        }

    @Transactional
    fun updateUserStatus(userId: Long, userStatusDTO: UserStatusDTO): UserProfileDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        user.status = userStatusDTO.status
        userRepository.save(user)
        return UserProfileDto.fromEntity(user)
    }

    fun listItems(status: String?, type: String?): List<ItemResponseDTO> {
        val items = itemRepository.findItemsByStatusAndType(status, type)
        return items.map { item ->
            val images = itemImageRepository.findImagesByItemId(item.id!!)
            val tags = tagRepository.findTagsByItemId(item.id!!)
            ItemResponseDTO.fromEntity(item, images, tags)
        }
    }

    @Transactional
    fun updateItemStatus(itemId: Long, itemApprovalDTO: ItemApprovalDTO): ItemResponseDTO {
        val item = itemRepository.findById(itemId).orElseThrow { IllegalArgumentException("Item not found") }
        item.status = itemApprovalDTO.status
        itemRepository.save(item)
        val images = itemImageRepository.findImagesByItemId(item.id!!)
        val tags = tagRepository.findTagsByItemId(item.id!!)
        return ItemResponseDTO.fromEntity(item, images, tags)
    }
}
