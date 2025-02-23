package com.donateraja.service

import com.donateraja.domain.admin.ItemApprovalDTO
import com.donateraja.domain.admin.ItemResponseDTO
import com.donateraja.domain.admin.UserProfileDto
import com.donateraja.domain.user.UserStatusDTO
import com.donateraja.entity.constants.UserStatus
import com.donateraja.entity.user.UserRole
import org.springframework.stereotype.Service

@Service
interface AdminService {

    fun listUsers(status: UserStatus?, role: UserRole?): List<UserProfileDto>
    fun updateUserStatus(userId: Long, userStatusDTO: UserStatusDTO): UserProfileDto
    fun listItems(status: String?, type: String?): List<ItemResponseDTO>
    fun updateItemStatus(itemId: Long, itemApprovalDTO: ItemApprovalDTO): ItemResponseDTO
}
