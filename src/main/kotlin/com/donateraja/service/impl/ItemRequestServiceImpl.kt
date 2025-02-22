package com.donateraja.service.impl

import com.donateraja.domain.request.ItemRequestCreateDTO
import com.donateraja.domain.request.ItemRequestResponseDTO
import com.donateraja.domain.request.ItemRequestStatusDTO
import com.donateraja.entity.ItemRequest
import com.donateraja.entity.constants.RequestStatus
import com.donateraja.repository.ItemRepository
import com.donateraja.repository.ItemRequestRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.ItemRequestService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ItemRequestServiceImpl(
    private val itemRequestRepository: ItemRequestRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : ItemRequestService {

    override fun createRequest(requestCreateDTO: ItemRequestCreateDTO): ItemRequestResponseDTO {
        val user = userRepository.findById(requestCreateDTO.userId!!)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        val item = itemRepository.findById(requestCreateDTO.itemId!!)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found") }

        val request = ItemRequest(
            userId = user.id,
            item = item,
            requestType = requestCreateDTO.requestType!!,
            message = requestCreateDTO.message,
            rentalStartDate = requestCreateDTO.rentalStartDate,
            rentalEndDate = requestCreateDTO.rentalEndDate,
            status = RequestStatus.PENDING
        )
        itemRequestRepository.save(request)
        return mapToDTO(request)
    }

    override fun getRequestById(requestId: Long): ItemRequestResponseDTO {
        val request = itemRequestRepository.findById(requestId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found") }
        return mapToDTO(request)
    }

    override fun updateRequestStatus(requestId: Long, requestStatusDTO: ItemRequestStatusDTO): ItemRequestResponseDTO {
        val request = itemRequestRepository.findById(requestId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found") }
        request.status = requestStatusDTO.status!!
        itemRequestRepository.save(request)
        return mapToDTO(request)
    }

    override fun getSentRequests(userId: Long): List<ItemRequestResponseDTO> = itemRequestRepository.findByUserId(userId).map {
        mapToDTO(it)
    }

    override fun getReceivedRequests(userId: Long): List<ItemRequestResponseDTO> =
        itemRequestRepository.findByItem_UserId(userId).map {
            mapToDTO(it)
        }

    private fun mapToDTO(request: ItemRequest): ItemRequestResponseDTO = ItemRequestResponseDTO(
        id = request.id,
        userId = request.userId,
        itemId = request.item.id,
        requestType = request.requestType,
        message = request.message ?: "",
        rentalStartDate = request.rentalStartDate,
        rentalEndDate = request.rentalEndDate,
        status = request.status,
        createdAt = request.createdAt,
        updatedAt = request.updatedAt
    )
}
