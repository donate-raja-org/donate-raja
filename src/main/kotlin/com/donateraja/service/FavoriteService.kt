package com.donateraja.service

import com.donateraja.domain.favorite.FavoriteResponseDTO

interface FavoriteService {
    fun addFavorite(itemId: Long): FavoriteResponseDTO
    fun getUserFavorites(): List<FavoriteResponseDTO>
    fun removeFavorite(itemId: Long)
}
