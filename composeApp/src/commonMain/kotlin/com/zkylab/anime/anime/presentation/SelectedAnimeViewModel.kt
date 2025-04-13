package com.zkylab.anime.anime.presentation

import androidx.lifecycle.ViewModel
import com.zkylab.anime.anime.domain.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedAnimeViewModel: ViewModel() {
    private val _selectedAnime = MutableStateFlow<Anime?>(null)
    val selectedAnime = _selectedAnime.asStateFlow()

    fun onSelectAnime(anime: Anime?) {
        _selectedAnime.value = anime
    }
}
