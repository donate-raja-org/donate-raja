package com.donateraja.service

import com.donateraja.domain.request.ItemRequestCreateDTO
import com.donateraja.domain.request.ItemRequestResponseDTO
import com.donateraja.domain.request.ItemRequestStatusDTO

interface ItemRequestService {
    fun createRequest(requestCreateDTO: ItemRequestCreateDTO): ItemRequestResponseDTO
    fun getRequestById(requestId: Long): ItemRequestResponseDTO
    fun updateRequestStatus(requestId: Long, requestStatusDTO: ItemRequestStatusDTO): ItemRequestResponseDTO
    fun getSentRequests(userId: Long): List<ItemRequestResponseDTO>
    fun getReceivedRequests(userId: Long): List<ItemRequestResponseDTO>
}
