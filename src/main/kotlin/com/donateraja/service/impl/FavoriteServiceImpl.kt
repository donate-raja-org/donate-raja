package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.favorite.FavoriteResponseDTO
import com.donateraja.entity.item.Favorite
import com.donateraja.repository.FavoriteRepository
import com.donateraja.repository.ItemRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.FavoriteService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteServiceImpl(
    private val favoriteRepository: FavoriteRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : FavoriteService {

    @Transactional
    override fun addFavorite(itemId: Long): FavoriteResponseDTO {
        val userEmail = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(userEmail)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        val item = itemRepository.findById(itemId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Item not found") }

        if (favoriteRepository.existsByUserIdAndItemId(user.id!!, item.id!!)) {
            throw ServiceException(HttpStatus.CONFLICT, "Item is already in favorites")
        }

        val favorite = Favorite(user = user, item = item)
        val savedFavorite = favoriteRepository.save(favorite)
        return FavoriteResponseDTO.fromEntity(savedFavorite)
    }

    override fun getUserFavorites(): List<FavoriteResponseDTO> {
        val userEmail = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(userEmail)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        val favorites = favoriteRepository.findByUserId(user.id!!)
        return favorites.map { FavoriteResponseDTO.fromEntity(it) }
    }

    @Transactional
    override fun removeFavorite(itemId: Long) {
        val userEmail = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(userEmail)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        if (!favoriteRepository.existsByUserIdAndItemId(user.id!!, itemId)) {
            throw ServiceException(HttpStatus.NOT_FOUND, "Favorite not found")
        }

        favoriteRepository.deleteByUserIdAndItemId(user.id!!, itemId)
    }
}
