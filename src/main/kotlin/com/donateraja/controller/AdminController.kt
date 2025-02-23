package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireAdmin
import com.donateraja.domain.admin.ItemApprovalDTO
import com.donateraja.domain.admin.ItemResponseDTO
import com.donateraja.domain.admin.UserProfileDto
import com.donateraja.domain.user.UserStatusDTO
import com.donateraja.entity.constants.UserStatus
import com.donateraja.entity.user.UserRole
import com.donateraja.service.AdminService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Management", description = "Admin operations for managing users and items")
class AdminController(private val adminService: AdminService) {

    @GetMapping("/users")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "List all users",
        description = "Fetches a list of all users based on status and role.",
        successSchema = List::class
    )
    fun listUsers(
        @RequestParam(required = false) status: UserStatus?,
        @RequestParam(required = false) role: UserRole?
    ): ResponseEntity<List<UserProfileDto>> = ResponseEntity.ok(adminService.listUsers(status, role))

    @PatchMapping("/users/{userId}/status")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Block/unblock user",
        description = "Updates the status of a user (e.g., active, blocked, banned).",
        successSchema = UserProfileDto::class
    )
    fun updateUserStatus(@PathVariable userId: Long, @RequestBody userStatusDTO: UserStatusDTO): ResponseEntity<UserProfileDto> =
        ResponseEntity.ok(adminService.updateUserStatus(userId, userStatusDTO))

    @GetMapping("/items")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "List all items",
        description = "Fetches a list of all items based on status and type.",
        successSchema = List::class
    )
    fun listItems(
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) type: String?
    ): ResponseEntity<List<ItemResponseDTO>> = ResponseEntity.ok(adminService.listItems(status, type))

    @PatchMapping("/items/{itemId}/status")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Approve/reject item",
        description = "Updates the approval status of an item.",
        successSchema = ItemResponseDTO::class
    )
    fun updateItemStatus(
        @PathVariable itemId: Long,
        @RequestBody itemApprovalDTO: ItemApprovalDTO
    ): ResponseEntity<ItemResponseDTO> = ResponseEntity.ok(adminService.updateItemStatus(itemId, itemApprovalDTO))
}
